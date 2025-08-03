package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder

//총계표 화면에서 문항별 통계 정보를 표현하기 위한 DTO
public class QuestionStatisticsDto {
    private Integer questionNo;               // 문항 번호
    private String questionName;              // 문항 내용
    private String subCategoryName;           // 하위역량 이름
    private int responseCount;                // 응답자 수
    private int[] choiceCounts;               // 보기 선택 수 [1~5점] → index 0=1점, 4=5점
    private BigDecimal averageScore;          // 평균 점수 (소수 2자리)
}
