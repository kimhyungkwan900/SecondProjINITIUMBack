package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 하위역량 정보 조회 DTO
public class SubCompetencyCategoryDto {
    private Long subCategoryId;     // 하위역량 ID
    private Long coreCategoryId;    // 상위 카테고리 ID
    private String subCategoryName; // 하위역량 이름
    private String description; // 하위역량 설명

}
