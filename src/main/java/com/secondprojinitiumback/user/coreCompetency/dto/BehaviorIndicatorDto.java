package com.secondprojinitiumback.user.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BehaviorIndicatorDto {
    private Long id;                // 행동 지표 ID
    private String behaviorName;    // 행동 지표 이름

    public static BehaviorIndicatorDto fromEntity(BehaviorIndicator behaviorIndicator){
        BehaviorIndicatorDto behaviorIndicatorDto = new BehaviorIndicatorDto();
        behaviorIndicatorDto.setId(behaviorIndicator.getId());
        behaviorIndicatorDto.setBehaviorName(behaviorIndicatorDto.getBehaviorName());
        return behaviorIndicatorDto;
    }
}
