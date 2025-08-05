package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.BehaviorIndicatorDto;
import com.secondprojinitiumback.user.coreCompetency.dto.IdealTalentProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdealTalentProfileService {

    private final IdealTalentProfileRepository idealTalentProfileRepository;

    public List<IdealTalentProfileDto> getIdealTalentStructure() {
        List<IdealTalentProfile> profiles = idealTalentProfileRepository.findAll();

        return profiles.stream().map(profile -> {
            CoreCompetencyCategory core = profile.getCoreCompetencyCategories();

            List<IdealTalentProfileDto.SubCompetencyCategoryDto> subDtos =
                    core.getSubCompetencyCategories().stream().map(sub -> {

                        List<IdealTalentProfileDto.BehaviorIndicatorDto> behaviorDtos =
                                sub.getBehaviorIndicators().stream().map(indicator -> {
                                    return IdealTalentProfileDto.BehaviorIndicatorDto.builder()
                                            .indicatorName(indicator.getName())
                                            .build();
                                }).collect(Collectors.toList());

                        return IdealTalentProfileDto.SubCompetencyCategoryDto.builder()
                                .subName(sub.getSubCategoryName())
                                .subDefinition(sub.getSubCategoryNote())
                                .behaviorIndicators(behaviorDtos)
                                .build();
                    }).collect(Collectors.toList());

            return IdealTalentProfileDto.builder()
                    .idealTalent(profile.getName())
                    .coreCompetencyCategories(List.of(
                            IdealTalentProfileDto.CoreCompetencyCategoryDto.builder()
                                    .coreName(core.getCoreCategoryName())
                                    .coreDefinition(core.getCoreCategoryNote())
                                    .subCompetencyCategories(subDtos)
                                    .build()
                    ))
                    .build();
        }).collect(Collectors.toList());
    }

}

