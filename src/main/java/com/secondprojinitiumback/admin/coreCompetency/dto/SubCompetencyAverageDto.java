package com.secondprojinitiumback.admin.coreCompetency.dto;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCompetencyAverageDto {

    private Long coreCategoryId;
    private String coreCategoryName;
    private Long subCategoryId;
    private String subCategoryName;
    private long questionCount; //문항의 수
    private double totalScore; //총점(합계)
    private double average; //평균(총점/문항의 수)
    private String level; //레벨 (우수/보통/미흡)
}
