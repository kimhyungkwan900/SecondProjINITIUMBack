package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorIndicatorDto {
    private Long id;                // 행동 지표 ID
    private String behaviorName;    // 행동 지표 이름
}
