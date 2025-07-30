package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 핵심역량 정보 조회 DTO
public class CoreCompetencyCategoryDto {
    private Long id;        // 핵심역량 카테고리 ID
    private String name;    // 핵심역량 이름
    private String description; // 핵심역량 설명
}
