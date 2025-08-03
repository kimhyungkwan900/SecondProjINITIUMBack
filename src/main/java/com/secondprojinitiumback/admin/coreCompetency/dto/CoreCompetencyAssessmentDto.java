package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//핵심역량진단목록 등록/수정/삭제/조회
public class CoreCompetencyAssessmentDto {
    private String assessmentNo;   // 평가 코드
    private String assessmentName; // 평가명
    private String startDate;      // 시작일 (yyyymmdd)
    private String endDate;        // 종료일
    private String registerDate;   // 등록일
    private String academicYear;   // 학년도
    private String semesterCode;   // 학기 코드
    private String onlineYn;       // 온라인 여부 ("Y" or "N")
    private String guideContent;   // 안내문
    private String departmentName;  //학과에 연결된 부서 -> 조회시 사용

    @Builder.Default
    private String semesterGroup = "SEMESTER";

    @Builder.Default
    private String onlineExecGroup = "ONLINE_YN";

}
