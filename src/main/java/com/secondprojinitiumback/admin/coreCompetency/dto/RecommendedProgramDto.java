package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 추천된 비교과 프로그램 정보 DTO
public class RecommendedProgramDto {
    private String id;               // 추천 ID
    private String programName;      // 프로그램명
    private String reasonContent;    // 추천 사유
}