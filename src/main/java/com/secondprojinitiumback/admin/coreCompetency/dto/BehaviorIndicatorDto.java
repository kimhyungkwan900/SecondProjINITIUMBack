package com.secondprojinitiumback.user.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyResponseDto;import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBehaviorIndicatorDto {
    private Long id;                // 행동 지표 ID
    private String behaviorName;    // 행동 지표 이름

    public static CoreCompetencyResponseDto.UserBehaviorIndicatorDto fromEntity(BehaviorIndicator behaviorIndicator){
        CoreCompetencyResponseDto.UserBehaviorIndicatorDto userBehaviorIndicatorDto = new CoreCompetencyResponseDto.UserBehaviorIndicatorDto();
        userBehaviorIndicatorDto.setId(behaviorIndicator.getId());
        userBehaviorIndicatorDto.setBehaviorName(userBehaviorIndicatorDto.getBehaviorName());
        return userBehaviorIndicatorDto;
    }
}
