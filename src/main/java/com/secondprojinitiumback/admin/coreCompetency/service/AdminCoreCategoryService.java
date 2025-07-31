package com.secondprojinitiumback.admin.coreCompetency.service;


import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.entity.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminCoreCategoryService {

    private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;
    private final IdealTalentProfileRepository idealTalentProfileRepository;

    //1. 핵심역량 카테고리 등록
    public CoreCompetencyCategory createCoreCategory(String name, String description, Long idealTalentProfileId) {

        Optional<IdealTalentProfile> idealTalentProfile = idealTalentProfileRepository.findById(idealTalentProfileId);

        if (idealTalentProfile.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 이상적인 인재 프로필입니다.");
        }

        CoreCompetencyCategory coreCompetencyCategory = CoreCompetencyCategory.builder()
                .coreCategoryName(name)
                .coreCategoryNote(description)
                .idealTalentProfile(idealTalentProfile.get())
                .build();

        return  coreCompetencyCategoryRepository.save(coreCompetencyCategory);
    }

    //2. 핵심역량 카테고리 수정
    public CoreCompetencyCategory updateCoreCategory(Long id, String name, String description) {
        CoreCompetencyCategory coreCompetencyCategory = coreCompetencyCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

        coreCompetencyCategory.setCoreCategoryName(name);
        coreCompetencyCategory.setCoreCategoryNote(description);

        return coreCompetencyCategoryRepository.save(coreCompetencyCategory);
    }

    //3. 핵심역량 카테고리 삭제
    public void deleteCoreCategory(Long id) {
        CoreCompetencyCategory coreCompetencyCategory = coreCompetencyCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

        coreCompetencyCategoryRepository.delete(coreCompetencyCategory);
    }

    //4. 핵심역량 카테고리 상세 조회
    public CoreCompetencyCategory getCoreCategory(Long id) {
        return coreCompetencyCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));
    }

    //5. 핵심역량 카테고리 전체 조회
    public List<CoreCompetencyCategory> getAllCoreCategories() {
        return coreCompetencyCategoryRepository.findAll();
    }

    //6. 카테고리 이름 중복 체크
    public boolean isCoreCategoryNameDuplicate(String name) {
        return coreCompetencyCategoryRepository.findAll().stream()
                .anyMatch(category -> category.getCoreCategoryName().equals(name));
    }
}
