package com.secondprojinitiumback.admin.coreCompetency.service;


import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.entity.IdealTalentProfile;
import com.secondprojinitiumback.admin.coreCompetency.entity.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.IdealTalentProfileRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCompetencyCategoryService {

    private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;
    private final IdealTalentProfileRepository idealTalentProfileRepository;
    private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;

    //1. 역량 카테고리 등록
    @Transactional
    public void createCategory(CompetencyCategoryDto competencyCategoryDto) {

        //핵심역량 등록 일 경우
        if("핵심역량".equalsIgnoreCase(competencyCategoryDto.getLevelType())){

            IdealTalentProfile idealTalentProfile = idealTalentProfileRepository.findById(competencyCategoryDto.getIdealTalentProfileId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인재상입니다."));

            CoreCompetencyCategory coreCompetencyCategory1 = CoreCompetencyCategory.builder()
                    .coreCategoryName(competencyCategoryDto.getName())
                    .coreCategoryNote(competencyCategoryDto.getDescription())
                    .idealTalentProfile(idealTalentProfile)
                    .build();

            coreCompetencyCategoryRepository.save(coreCompetencyCategory1);
        }
        //하위역량 등록 일 경우
        else {
            CoreCompetencyCategory coreCompetencyCategory = coreCompetencyCategoryRepository.findById(competencyCategoryDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

            SubCompetencyCategory subCompetencyCategory = SubCompetencyCategory.builder()
                    .subCategoryName(competencyCategoryDto.getName())
                    .subCategoryNote(competencyCategoryDto.getDescription())
                    .coreCompetencyCategory(coreCompetencyCategory)
                    .build();

            subCompetencyCategoryRepository.save(subCompetencyCategory);
        }
    }

    //2. 역량 카테고리 수정
    @Transactional
    public void updateCategory(Long id, CompetencyCategoryDto competencyCategoryDto) {

        //핵심역량 수정 일 경우
        if("핵심역량".equalsIgnoreCase(competencyCategoryDto.getLevelType())){
            CoreCompetencyCategory coreCompetencyCategory = coreCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

            IdealTalentProfile idealTalentProfile = idealTalentProfileRepository.findById(competencyCategoryDto.getIdealTalentProfileId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 인재상입니다."));

            coreCompetencyCategory.setCoreCategoryName(competencyCategoryDto.getName());
            coreCompetencyCategory.setCoreCategoryNote(competencyCategoryDto.getDescription());
            coreCompetencyCategory.setIdealTalentProfile(idealTalentProfile);

            coreCompetencyCategoryRepository.save(coreCompetencyCategory);
        }
        //하위역량 수정 일 경우
        else {
            SubCompetencyCategory subCompetencyCategory = subCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위 역량 카테고리입니다."));

            subCompetencyCategory.setSubCategoryName(competencyCategoryDto.getName());
            subCompetencyCategory.setSubCategoryNote(competencyCategoryDto.getDescription());

            subCompetencyCategoryRepository.save(subCompetencyCategory);
        }
    }

    //3. 역량 카테고리 삭제
    @Transactional
    public void deleteCategory(String levelType, Long id) {
        if ("핵심역량".equals(levelType)) {
            CoreCompetencyCategory core = coreCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("핵심역량 없음"));
            coreCompetencyCategoryRepository.delete(core);
        } else {
            SubCompetencyCategory sub = subCompetencyCategoryRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("하위역량 없음"));
            subCompetencyCategoryRepository.delete(sub);
        }
    }

    //4. 역량 카테고리 조회
    public List<CoreCompetencyCategory> getAllCoreCompetencyCategories() {
        return coreCompetencyCategoryRepository.findAll();
    }
    public List<SubCompetencyCategory> getAllSubCompetencyCategories() {
        return subCompetencyCategoryRepository.findAll();
    }

    // 6. 중복 체크
    public boolean isCoreCategoryNameDuplicate(String name) {
        return coreCompetencyCategoryRepository.findAll().stream()
                .anyMatch(c -> c.getCoreCategoryName().equals(name));
    }

    public boolean isSubCategoryNameDuplicate(Long coreCategoryId, String name) {
        return subCompetencyCategoryRepository.findByCoreCategoryId(coreCategoryId).stream()
                .anyMatch(sub -> sub.getSubCategoryName().equalsIgnoreCase(name));
    }
}
