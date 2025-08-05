package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.IdealTalentProfileDto;
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
    public List<IdealTalentProfileDto> getIdealTalentStructure() {
        // 1. 전체 인재상(IdealTalentProfile) 엔티티를 DB에서 조회
        return idealTalentProfileRepository.findAll().stream()

                // 2. 각 인재상(profile)을 IdealTalentProfileDto로 변환
                .map(profile -> {
                    // 인재상과 1:1로 연결된 핵심역량 엔티티 조회
                    CoreCompetencyCategory core = profile.getCoreCompetencyCategories();

                    // 핵심역량에 연결된 하위역량 목록을 DTO 리스트로 변환
                    List<IdealTalentProfileDto.SubCompetencyCategoryDto> subCompetencyCategoryDto =
                            core.getSubCompetencyCategories().stream()
                                    .map(sub ->
                                            IdealTalentProfileDto.SubCompetencyCategoryDto.builder()
                                                    .subName(sub.getSubCategoryName())           // 하위역량명
                                                    .subDefinition(sub.getSubCategoryNote())     // 하위역량 설명
                                                    .build())
                                    .collect(Collectors.toList());

                    // 인재상 DTO 생성 및 반환
                    return IdealTalentProfileDto.builder()
                            .idealTalent(profile.getName()) // 인재상명
                            .coreCompetencyCategories(List.of( // 핵심역량은 1:1이므로 리스트에 한 개만 포함
                                    IdealTalentProfileDto.CoreCompetencyCategoryDto.builder()
                                            .coreName(core.getCoreCategoryName())           // 핵심역량명
                                            .coreDefinition(core.getCoreCategoryNote())     // 핵심역량 설명
                                            .subCompetencyCategories(subCompetencyCategoryDto) // 하위역량 리스트 포함
                                            .build()
                            ))
                            .build();
                })

                // 3. 전체 결과 리스트로 수집
                .collect(Collectors.toList());
    }
}
