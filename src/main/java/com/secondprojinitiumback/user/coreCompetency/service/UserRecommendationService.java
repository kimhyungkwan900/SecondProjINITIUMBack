package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyAverageDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCompetencyResultService;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularImageDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularImageRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserRecommendProgramDto;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularProgramDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {

    private final ExtracurricularProgramRepository programRepository;
    private final AdminCoreCompetencyResultService resultService;

    private final ExtracurricularApplyRepository applyRepository;
    private final ExtracurricularImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<UserRecommendProgramDto> findRecommendedPrograms(
            String assessmentNo, String studentNo, int limit) {

        // 미흡 하위역량 추출
        var subIds = resultService.getSubCompetencyAverages(assessmentNo, studentNo).stream()
                .filter(dto -> "미흡".equals(dto.getLevel()))
                .map(SubCompetencyAverageDto::getSubCategoryId)
                .distinct()
                .toList();

        if (subIds.isEmpty()) return List.of();

        // 프로그램 조회 (가능하면 승인/마감미도래 필터+정렬이 걸린 메서드 사용 권장)
        List<ExtracurricularProgram> programs =
                programRepository.findProgramsBySubIds(subIds);

        if (limit > 0 && programs.size() > limit) {
            programs = programs.subList(0, limit);
        }

        // === N+1 회피용 배치 데이터 수집 ===
        List<Long> programIds = programs.stream()
                .map(ExtracurricularProgram::getEduMngId)
                .toList();

        // 참여수 집계 맵
        Map<Long, Integer> acceptMap = buildAcceptCountMap(programIds);

        // 이미지 맵 (프로그램별 이미지 리스트)
        Map<Long, List<ExtracurricularImage>> imagesMap = buildImagesMap(programIds);

        // DTO 변환(매퍼 없이 직접)
        return programs.stream().map(p -> {
            ExtracurricularProgramDTO programDto =
                    toProgramDtoNoMapper(p, acceptMap, imagesMap);

            return UserRecommendProgramDto.builder()
                    // 기존 테이블용 탑레벨 필드 유지
                    .programId(p.getEduMngId())
                    .eduNm(p.getEduNm())
                    .subCategoryId(
                            p.getExtracurricularCategory() != null
                                    ? p.getExtracurricularCategory().getStgrId() : null
                    )
                    .studentNo(studentNo)
                    .assessmentNo(assessmentNo)
                    .eduMlg(p.getEduMlg())
                    .eduAplyBgngDt(p.getEduAplyBgngDt())
                    .eduAplyEndDt(p.getEduAplyEndDt())
                    .eduBgngYmd(p.getEduBgngYmd())
                    .eduEndYmd(p.getEduEndYmd())

                    // 카드용 세부 컨테이너
                    .program(programDto)
                    .build();
        }).toList();
    }

    // 참여수 맵 구성
    private Map<Long, Integer> buildAcceptCountMap(List<Long> programIds) {
        if (programIds.isEmpty()) return Map.of();
        List<Object[]> rows = applyRepository.countAcceptedByProgramIds(programIds);
        Map<Long, Integer> map = new HashMap<>();
        for (Object[] r : rows) {
            Long pid = (Long) r[0];
            Long cnt = (Long) r[1]; // JPA가 Long으로 줄 가능성 높음
            map.put(pid, cnt != null ? cnt.intValue() : 0);
        }
        return map;
    }

    // 이미지 맵 구성
    private Map<Long, List<ExtracurricularImage>> buildImagesMap(List<Long> programIds) {
        if (programIds.isEmpty()) return Map.of();
        List<ExtracurricularImage> list = imageRepository.findAllByProgramIds(programIds);
        return list.stream().collect(Collectors.groupingBy(
                img -> img.getExtracurricularProgram().getEduMngId(),
                LinkedHashMap::new,
                Collectors.toList()
        ));
    }

    // === 매퍼 대체: 서비스 내부 경량 어셈블러 ===
    private ExtracurricularProgramDTO toProgramDtoNoMapper(
            ExtracurricularProgram p,
            Map<Long, Integer> acceptMap,
            Map<Long, List<ExtracurricularImage>> imagesMap) {

        Long pid = p.getEduMngId();
        int accept = acceptMap.getOrDefault(pid, 0);

        // 이미지 엔티티 → DTO 변환 (첫 장을 대표로 쓰더라도 리스트로 유지)
        List<ExtracurricularImageDTO> imageDtos = null;
        List<ExtracurricularImage> imgs = imagesMap.get(pid);
        if (imgs != null && !imgs.isEmpty()) {
            imageDtos = imgs.stream()
                    .map(i -> ExtracurricularImageDTO.builder()
                            .imgFilePathNm(i.getImgFilePathNm())
                            // .thumbnail(i.getThumbnail()) // 필드가 있다면
                            .build())
                    .toList();
        } else {
            imageDtos = List.of(); // 프론트 기본 이미지 사용
        }

        // 카테고리명
        String ctgryNm = null;
        if (p.getExtracurricularCategory() != null) {
            ctgryNm = p.getExtracurricularCategory().getCtgryNm();
        }

        return ExtracurricularProgramDTO.builder()
                .eduMngId(pid)
                .ctgryId(p.getExtracurricularCategory() != null
                        ? p.getExtracurricularCategory().getCtgryId() : null)
                .empNo(p.getEmployee() != null ? p.getEmployee().getEmpNo() : null)

                .eduNm(p.getEduNm())
                .eduType(p.getEduType())
                .eduTrgtLmt(p.getEduTrgtLmt())
                .eduGndrLmt(p.getEduGndrLmt())
                .eduSlctnType(p.getEduSlctnType())
                .eduPtcpNope(p.getEduPtcpNope())
                .eduPrps(p.getEduPrps())
                .eduDtlCn(p.getEduDtlCn())
                .eduAplyBgngDt(p.getEduAplyBgngDt())
                .eduAplyEndDt(p.getEduAplyEndDt())
                .eduBgngYmd(p.getEduBgngYmd())
                .eduEndYmd(p.getEduEndYmd())
                .eduPlcNm(p.getEduPlcNm())
                .eduAplyDt(p.getEduAplyDt())
                .sttsNm(p.getSttsNm())
                .eduMlg(p.getEduMlg())
                .sttsChgDt(p.getSttsChgDt())
                .cndCn(p.getCndCn())
                .field(p.getFileNo())
                .fileNo(p.getFileNo())

                .accept(accept)
                .extracurricularImageDTO(imageDtos)
                .ctgryNm(ctgryNm)

                // 운영자 정보가 필요하면 아래도 채움(엔티티에 관계 있으면)
                .name(p.getEmployee()!=null ? p.getEmployee().getName() : null)
                .email(p.getEmployee()!=null ? p.getEmployee().getEmail() : null)
                .subjectName(p.getEmployee()!=null && p.getEmployee().getSchoolSubject()!=null
                        ? p.getEmployee().getSchoolSubject().getSubjectCode() : null)
                .tel(p.getEmployee()!=null ? p.getEmployee().getTel() : null)

                // 일정 DTO가 필요하면 별도 쿼리 or 관계에서 매핑
                .extracurricularSchedules(List.of())
                .build();
    }
}
