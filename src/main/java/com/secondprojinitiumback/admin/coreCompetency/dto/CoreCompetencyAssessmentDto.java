package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import lombok.*;

/**
 * 📄 CoreCompetencyAssessmentDto
 * 핵심역량진단 평가 정보를 프론트로 전달하거나 저장 시 사용하는 DTO
 * - 진단목록 조회, 등록, 수정, 삭제에 모두 사용됨
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoreCompetencyAssessmentDto {

    /** 진단 평가 고유 번호 (예: ASMT2025-01) */
    private String assessmentNo;

    /** 진단 평가명 */
    private String assessmentName;

    /** 진단 시작일 (yyyymmdd 형식) */
    private String startDate;

    /** 진단 종료일 (yyyymmdd 형식) */
    private String endDate;

    /** 등록일자 (yyyymmdd 형식) */
    private String registerDate;

    /** 학년도 (예: 2025) */
    private String academicYear;

    /** 학기 코드명 (예: 1학기 / 2학기) */
    private String semesterCode;

    /** 온라인/오프라인 여부 코드명 ("온라인" or "오프라인") */
    private String onlineYn;

    /** 진단 평가 안내문 */
    private String guideContent;

    /** 부서명 (SchoolSubject에 연결된 부서 이름) */
    private String departmentName;

    /** 결과표 차트 표시 방식 (예: "개인별+전체") */
    private String chart = "개인별+전체";

    /** 분석 기준 (예: "평균") */
    private String analysisStandard = "평균";

    /** 📌 학기 그룹 코드 (프론트에서 select box 옵션용) */
    @Builder.Default
    private String semesterGroup = "SEMESTER";

    /** 📌 온라인 여부 그룹 코드 (프론트에서 select box 옵션용) */
    @Builder.Default
    private String onlineExecGroup = "ONLINE_YN";

    /**
     * ✅ Entity → Dto 변환 (상세 조회용)
     * - 평가 기본정보 + 등록일 + 학기/온라인 코드명 포함
     */
    public static CoreCompetencyAssessmentDto fromEntity(CoreCompetencyAssessment assessment) {
        return CoreCompetencyAssessmentDto.builder()
                .assessmentNo(assessment.getAssessmentNo())
                .assessmentName(assessment.getAssessmentName())
                .startDate(assessment.getStartDate())
                .endDate(assessment.getEndDate())
                .registerDate(assessment.getRegisterDate())
                .academicYear(assessment.getAcademicYear())
                .guideContent(assessment.getGuideContent())
                .departmentName(assessment.getSchoolSubject() != null ? assessment.getSchoolSubject().getSubjectName() : null)
                .onlineYn(assessment.getOnlineExecCode().getCodeName())  // "Y"/"N" 코드명이 아닌 "온라인"/"오프라인"
                .semesterCode(assessment.getSemesterCode().getCodeName()) // "1학기"/"2학기"
                .build();
    }

    /**
     * ✅ Entity → Dto 변환 (차트/분석 기준만 필요한 경우)
     * - 결과표에서 사용됨
     */
    public static CoreCompetencyAssessmentDto fromEntity2(CoreCompetencyAssessment assessment) {
        return CoreCompetencyAssessmentDto.builder()
                .assessmentNo(assessment.getAssessmentNo())
                .assessmentName(assessment.getAssessmentName())
                .startDate(assessment.getStartDate())
                .endDate(assessment.getEndDate())
                .chart(assessment.getChartType())                 // 개인별/전체 등
                .analysisStandard(assessment.getAnalysisType())   // 평균/최빈값 등
                .build();
    }
}
