package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCompetencyCategoryDto {

    private Long id; // 하위 역량 카테고리 ID (PK)
    private String name; // 하위 역량 카테고리 이름
    private String description; // 하위 역량 카테고리 설명 (추가 가능성 있음)
    private String codeName; // 하위 역량 카테고리 코드
    private Long assessmentId; // 평가 ID (추가 가능성 있음)
    private Long coreCategoryId;    //핵심역량 Id

    public static SubCompetencyCategoryDto fromEntity(SubCompetencyCategory subCompetencyCategory) {
        return SubCompetencyCategoryDto.builder()
                .id(subCompetencyCategory.getId())
                .name(subCompetencyCategory.getSubCategoryName())
                .description(subCompetencyCategory.getSubCategoryNote())
                .codeName(subCompetencyCategory.getCompetencyCategory().getCodeName())
                .build();
    }

    public static SubCompetencyCategoryDto fromEntity2(SubCompetencyCategory subCompetencyCategory) {
        return SubCompetencyCategoryDto.builder()
                .id(subCompetencyCategory.getId())
                .name(subCompetencyCategory.getSubCategoryName())
                .assessmentId(subCompetencyCategory.getCoreCompetencyCategory().getAssessment().getId())
                .build();
    }
}
