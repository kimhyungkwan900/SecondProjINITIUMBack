package com.secondprojinitiumback.admin.coreCompetency.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompetencyCategoryDto {
    private Long id; // 수정 시 사용
    private String name;             // 분석기준명
    private String description;      // 분석기준 설명
    private String levelType;        // "핵심역량" 또는 "하위역량"
    private Long parentId;           // 상위 카테고리 ID (하위역량일 경우 필수)
    private Long idealTalentProfileId; // 핵심역량일 경우 자동으로 인재상 ID 설정됨
}
