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
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {

    private static final String LEVEL_LOW = "미흡"; // TODO: Enum/상수화

    private final ExtracurricularProgramRepository programRepository;
    private final AdminCoreCompetencyResultService resultService;
    private final ExtracurricularApplyRepository applyRepository;
    private final ExtracurricularImageRepository imageRepository;

    // =========================
    // 오버로드 1) 기존 limit 버전 (기존 호출부 호환)
    // =========================
    @Transactional(readOnly = true)
    public List<UserRecommendProgramDto> findRecommendedPrograms(
            String assessmentNo, String studentNo, int limit) {

        // 1) 미흡 하위역량 추출
        List<Long> subIds = extractLowSubIds(assessmentNo, studentNo);
        if (subIds.isEmpty()) return List.of();

        // 2) 프로그램 후보 조회 (간단 버전)
        List<ExtracurricularProgram> programs = programRepository.findProgramsBySubIds(subIds);

        // 3) 이미 신청/수료 제외
        List<ExtracurricularProgram> filtered = postFilterAppliedOrCompleted(programs, studentNo);

        // 4) limit 적용
        if (limit > 0 && filtered.size() > limit) {
            filtered = filtered.subList(0, limit);
        }

        // 5) DTO 조립
        return assembleDtos(filtered, studentNo, assessmentNo);
    }

    // =========================
    // 오버로드 2) 페이지네이션/정렬 버전 (신규)
    // =========================
    @Transactional(readOnly = true)
    public Page<UserRecommendProgramDto> findRecommendedPrograms(
            String assessmentNo, String studentNo, int page, int size, String sortKey) {

        // 1) 미흡 하위역량 추출
        List<Long> subIds = extractLowSubIds(assessmentNo, studentNo);
        if (subIds.isEmpty()) return Page.empty(PageRequest.of(page, size));

        // 2) 정렬/페이징 구성
        Sort sort = switch (Optional.ofNullable(sortKey).orElse("LATEST").toUpperCase()) {
            case "POPULAR" -> Sort.by(Sort.Direction.DESC, "eduAplyEndDt", "eduPtcpNope"); // 예시
            case "SOON"    -> Sort.by(Sort.Direction.ASC, "eduAplyEndDt");
            default        -> Sort.by(Sort.Direction.DESC, "eduAplyBgngDt");
        };
        Pageable pageable = PageRequest.of(page, size, sort);

        // 3) 정책 포함 추천 후보 조회 (상태/기간/성별/대상 등은 쿼리에서 필터)
        Page<ExtracurricularProgram> programs =
                programRepository.findRecommendedBySubIdsWithPolicy(subIds, pageable);

        if (programs.isEmpty()) return programs.map(p -> null);

        // 4) 이미 신청/수료 제외
        List<ExtracurricularProgram> filtered =
                postFilterAppliedOrCompleted(programs.getContent(), studentNo);

        // 5) DTO 조립
        List<UserRecommendProgramDto> dtoList =
                assembleDtos(filtered, studentNo, assessmentNo);

        // 6) 페이지 래핑 (total은 원본 page total 유지)
        return new PageImpl<>(dtoList, pageable, programs.getTotalElements());
    }

    // =========================
    // 공통 유틸 (중복 제거)
    // =========================

    /** 미흡 하위역량(STGR_ID) 목록 추출 */
    private List<Long> extractLowSubIds(String assessmentNo, String studentNo) {
        return resultService.getSubCompetencyAverages(assessmentNo, studentNo).stream()
                .filter(dto -> LEVEL_LOW.equals(dto.getLevel()))
                .map(SubCompetencyAverageDto::getSubCategoryId)
                .distinct()
                .toList();
    }

    /** 이미 신청/수료한 프로그램 제외 */
    private List<ExtracurricularProgram> postFilterAppliedOrCompleted(
            List<ExtracurricularProgram> candidates, String studentNo) {

        if (candidates.isEmpty()) return candidates;
        Set<Long> appliedOrCompleted = applyRepository.findAppliedOrCompletedProgramIds(studentNo);
        if (appliedOrCompleted == null || appliedOrCompleted.isEmpty()) return candidates;

        return candidates.stream()
                .filter(p -> !appliedOrCompleted.contains(p.getEduMngId()))
                .toList();
    }

    /** 배치 데이터 수집 후 DTO 조립 */
    private List<UserRecommendProgramDto> assembleDtos(
            List<ExtracurricularProgram> programs, String studentNo, String assessmentNo) {

        if (programs.isEmpty()) return List.of();

        // 배치 키 수집
        List<Long> programIds = programs.stream()
                .map(ExtracurricularProgram::getEduMngId)
                .toList();

        // 참여수 집계
        Map<Long, Integer> acceptMap = buildAcceptCountMap(programIds);

        // 이미지 묶음
        Map<Long, List<ExtracurricularImage>> imagesMap = buildImagesMap(programIds);

        // DTO 변환
        return programs.stream().map(p -> {
            ExtracurricularProgramDTO programDto =
                    toProgramDtoNoMapper(p, acceptMap, imagesMap);

            return UserRecommendProgramDto.builder()
                    .programId(p.getEduMngId())
                    .eduNm(p.getEduNm())
                    .subCategoryId(p.getExtracurricularCategory() != null
                            ? p.getExtracurricularCategory().getStgrId() : null)
                    .studentNo(studentNo)
                    .assessmentNo(assessmentNo)
                    .eduMlg(p.getEduMlg())
                    .eduAplyBgngDt(p.getEduAplyBgngDt())
                    .eduAplyEndDt(p.getEduAplyEndDt())
                    .eduBgngYmd(p.getEduBgngYmd())
                    .eduEndYmd(p.getEduEndYmd())
                    .program(programDto)
                    .build();
        }).toList();
    }

    /** 참여수 맵 구성 */
    private Map<Long, Integer> buildAcceptCountMap(List<Long> programIds) {
        if (programIds.isEmpty()) return Map.of();
        List<Object[]> rows = applyRepository.countAcceptedByProgramIds(programIds);
        Map<Long, Integer> map = new HashMap<>();
        for (Object[] r : rows) {
            Long pid = ((Number) r[0]).longValue();
            Integer cnt = ((Number) r[1]).intValue();
            map.put(pid, cnt);
        }
        return map;
    }

    /** 이미지 맵 구성 */
    private Map<Long, List<ExtracurricularImage>> buildImagesMap(List<Long> programIds) {
        if (programIds.isEmpty()) return Map.of();
        List<ExtracurricularImage> list = imageRepository.findAllByProgramIds(programIds);
        return list.stream().collect(Collectors.groupingBy(
                img -> img.getExtracurricularProgram().getEduMngId(),
                LinkedHashMap::new,
                Collectors.toList()
        ));
    }

    /** 경량 어셈블러 (엔티티 → Program DTO) */
    private ExtracurricularProgramDTO toProgramDtoNoMapper(
            ExtracurricularProgram p,
            Map<Long, Integer> acceptMap,
            Map<Long, List<ExtracurricularImage>> imagesMap) {

        Long pid = p.getEduMngId();
        int accept = acceptMap.getOrDefault(pid, 0);

        List<ExtracurricularImageDTO> imageDtos =
                imagesMap.getOrDefault(pid, List.of()).stream()
                        // 중요: 엔티티에 @Column(name="IMG_FLIE_PATH_NM") 매핑 필요
                        .map(i -> ExtracurricularImageDTO.builder()
                                .imgFilePathNm(i.getImgFilePathNm())
                                .build())
                        .toList();

        String ctgryNm = p.getExtracurricularCategory() != null
                ? p.getExtracurricularCategory().getCtgryNm() : null;

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

                .name(p.getEmployee()!=null ? p.getEmployee().getName() : null)
                .email(p.getEmployee()!=null ? p.getEmployee().getEmail() : null)
                .subjectName(p.getEmployee()!=null && p.getEmployee().getSchoolSubject()!=null
                        ? p.getEmployee().getSchoolSubject().getSubjectCode() : null)
                .tel(p.getEmployee()!=null ? p.getEmployee().getTel() : null)

                .extracurricularSchedules(List.of())
                .build();
    }
}
