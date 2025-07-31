package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 보기(객관식 선택지) 정보 DTO
public class ResponseChoiceOptionDto {
    private Long optionId;           // 선택지 ID
    private Long questionId;         // 문항 ID
    private String label;            // 보기 내용
    private Integer score;           // 배점
}

