package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdealTalentProfileDto {
    private Long id;
    private String name;

    public static IdealTalentProfileDto fromEntity(IdealTalentProfile profile) {
        return IdealTalentProfileDto.builder()
                .id(profile.getId())
                .name(profile.getName())
                .build();
    }
}