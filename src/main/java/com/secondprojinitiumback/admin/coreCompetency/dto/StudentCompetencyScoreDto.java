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
    private String subCategoryName;    // 하위역량명
    private String questionName;
    private int respondent; //응답자 수
    private BigDecimal standardScore;  // 개인 점수
    private BigDecimal totalAverageScore; // 전체 평균 점수

    public StudentCompetencyScoreDto(Long id, String subCategoryName, BigDecimal standardScore, BigDecimal totalAverageScore) {
        this.id = id;
        this.subCategoryName = subCategoryName;
        this.standardScore = standardScore;
        this.totalAverageScore = totalAverageScore;
    }

    //문항별 합계

}
