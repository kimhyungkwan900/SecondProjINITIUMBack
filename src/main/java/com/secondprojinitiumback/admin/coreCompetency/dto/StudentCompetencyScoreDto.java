package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

import java.math.BigDecimal;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 학생의 역량 점수 조회용 DTO
public class StudentCompetencyScoreDto {
    private Long id;                   // 점수 ID
    private Long resultId;             // 진단 결과 ID
    private String coreCategoryName;   // 핵심역량명
    private String subCategoryName;    // 하위역량명
    private BigDecimal standardScore;  // 개인 점수
    private BigDecimal totalAverageScore; // 전체 평균 점수
}

