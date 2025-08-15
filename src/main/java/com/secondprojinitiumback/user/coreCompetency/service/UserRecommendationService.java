package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyAverageDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCompetencyResultService;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserRecommendProgramDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {

        private final ExtracurricularProgramRepository programRepository;
        private final AdminCoreCompetencyResultService resultService;

        /**
         * 실시간으로 미흡 역량에 대한 추천 프로그램을 조회하여 DTO 리스트로 반환합니다. (DB 저장 X)
         */
        @Transactional
        public List<UserRecommendProgramDto> findRecommendedPrograms(String assessmentNo, String studentNo, int limit) {

            // 1) 미흡 하위역량 수집
            List<SubCompetencyAverageDto> averages =
                    resultService.getSubCompetencyAverages(assessmentNo, studentNo);

            List<Long> poorSubIds = new ArrayList<>();
            for (SubCompetencyAverageDto dto : averages) {
                if ("미흡".equals(dto.getLevel())) {
                    poorSubIds.add(dto.getSubCategoryId());
                }
            }
            if (poorSubIds.isEmpty()) return List.of();

            // 2) 하위역량으로 프로그램 조회
            List<ExtracurricularProgram> programs =
                    programRepository.findProgramsBySubIds(poorSubIds);

            // 2-1) 모집 마감(LocalDateTime) 오름차순, null은 뒤로
            programs.sort(Comparator.comparing(
                    ExtracurricularProgram::getEduAplyEndDt,
                    Comparator.nullsLast(Comparator.reverseOrder())
            ));

            // 2-2) 개수 제한
            if (limit > 0 && programs.size() > limit) {
                programs = programs.subList(0, limit);
            }

            // 3) DTO 변환 (DTO도 LocalDateTime)
            return programs.stream()
                    .map(prog -> UserRecommendProgramDto.builder()
                            .programId(prog.getEduMngId())
                            .studentNo(studentNo)
                            .assessmentNo(assessmentNo)
                            .eduNm(prog.getEduNm())
                            .eduMlg(prog.getEduMlg())
                            .subCategoryId(
                                    prog.getExtracurricularCategory().getStgrId()
                                    // 또는 prog.getExtracurricularCategory().getSubCompetency().getId()
                            )
                            .eduAplyBgngDt(prog.getEduAplyBgngDt())
                            .eduAplyEndDt(prog.getEduAplyEndDt())   // LocalDateTime 그대로 매핑
                            .eduBgngYmd(prog.getEduBgngYmd())
                            .eduEndYmd(prog.getEduEndYmd())
                            .build())
                    .toList();
        }

}
