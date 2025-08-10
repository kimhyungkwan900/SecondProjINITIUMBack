package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserIdealTalentProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자용 인재상-핵심역량-하위역량 계층 구조 조회 서비스 클래스
 * 구조: 인재상 → 핵심역량(1:1) → 하위역량들(1:N)
 */
@Service
@RequiredArgsConstructor
public class IdealTalentProfileService {

    private final IdealTalentProfileRepository idealTalentProfileRepository;

    /**
     * 인재상-핵심역량-하위역량 트리 구조를 조회하여 DTO로 변환
     *
     * @return List<IdealTalentProfileDto> - 계층 구조를 반영한 인재상 정보 리스트
     */
    public List<UserIdealTalentProfileDto> getIdealTalentStructure() {
        // 1. 전체 인재상(IdealTalentProfile)과 연결되어 있는 핵심/하위역량 정보를 조회
        return idealTalentProfileRepository.findAllWithCoreAndSub().stream()

                // 2. 각 인재상(profile)을 IdealTalentProfileDto로 변환
                .map(profile -> {
                    // 인재상에 연결된 모든 핵심역량 리스트
                    List<UserIdealTalentProfileDto.CoreCompetencyCategoryDto> coreCompetencyDtos = profile.getCoreCompetencyCategories().stream()
                            .map(core -> {
                                List<UserIdealTalentProfileDto.SubCompetencyCategoryDto> subDtos = core.getSubCompetencyCategories().stream()
                                        .map(sub -> UserIdealTalentProfileDto.SubCompetencyCategoryDto.builder()
                                                .subName(sub.getSubCategoryName())
                                                .subDefinition(sub.getSubCategoryNote())
                                                .build())
                                        .collect(Collectors.toList());

                                return UserIdealTalentProfileDto.CoreCompetencyCategoryDto.builder()
                                        .coreName(core.getCoreCategoryName())
                                        .coreDefinition(core.getCoreCategoryNote())
                                        .subCompetencyCategories(subDtos)
                                        .build();
                            }).collect(Collectors.toList());

                    return UserIdealTalentProfileDto.builder()
                            .idealTalent(profile.getName())
                            .coreCompetencyCategories(coreCompetencyDtos)
                            .build();
                })

                // 3. 전체 결과 리스트로 수집
                .collect(Collectors.toList());
    }
}
