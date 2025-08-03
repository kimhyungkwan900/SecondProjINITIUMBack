package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreLevelDto {
    private Integer minScore;      // 최소 점수
    private Integer maxScore;      // 최대 점수
    private String levelName;      // 레벨 이름
    private String description;    // 해석 설명
}
