package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCompetencyLevelRowDto {

    private Long coreCategoryId;
    private String coreCategoryName;
    private Long subCategoryId;
    private String subCategoryName;
    private double myScore; //나의 점수
    private double average; //전체 평균
    private String level;   //우수/보통/미흡


}
