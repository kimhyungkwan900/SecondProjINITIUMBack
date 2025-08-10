package com.secondprojinitiumback.user.coreCompetency.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class UserIdealTalentProfileDto {
    private String idealTalent;
    private List<CoreCompetencyCategoryDto> coreCompetencyCategories;

    @Getter @Setter @Builder
    public static class CoreCompetencyCategoryDto {
        private String coreName;
        private String coreDefinition;
        private List<SubCompetencyCategoryDto> subCompetencyCategories;
    }

    @Getter @Setter @Builder
    public static class SubCompetencyCategoryDto {
        private String subName;
        private String subDefinition;
    }
}

