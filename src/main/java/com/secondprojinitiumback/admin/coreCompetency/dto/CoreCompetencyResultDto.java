package com.secondprojinitiumback.admin.coreCompetency.dto;

// 평가 결과 정보 조회용 DTO
public class CoreCompetencyResultDto {
    private Long id;                 // 결과 ID
    private Long assessmentId;       // 평가 ID
    private String assessmentName;   // 평가명
    private Long responseId;         // 응답 ID
    private String classificationCode; // 결과 분류 코드
}

