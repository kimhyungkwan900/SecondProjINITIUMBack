package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.common.domain.CommonCode;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoreCompetencyCategoryDto {

    private String assessmentNo; // 평가 ID
    private Long id; // 핵심 역량 카테고리 ID (PK)
    private String name; // 핵심 역량 카테고리 이름
    private String description; // 핵심 역량 카테고리 설명 (추가 가능성 있음)
    private String codeName;
    private List<SubCompetencyCategoryDto> subCompetencyCategories; // 하위 역량 카테고리 목록


    public static CoreCompetencyCategoryDto fromEntity(CoreCompetencyCategory coreCompetencyCategory) {
        return CoreCompetencyCategoryDto.builder()
                .assessmentNo(coreCompetencyCategory.getAssessment().getAssessmentNo())
                .id(coreCompetencyCategory.getId())
                .name(coreCompetencyCategory.getCoreCategoryName())
                .description(coreCompetencyCategory.getCoreCategoryNote())
                .codeName(coreCompetencyCategory.getCompetencyCategory().getCodeName())
                .subCompetencyCategories(coreCompetencyCategory.getSubCompetencyCategories()
                        .stream().map(SubCompetencyCategoryDto::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
