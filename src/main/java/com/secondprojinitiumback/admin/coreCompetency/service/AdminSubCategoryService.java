package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.entity.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyCategoryRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.SubCompetencyCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminSubCategoryService {

        private final SubCompetencyCategoryRepository subCompetencyCategoryRepository;
        private final CoreCompetencyCategoryRepository coreCompetencyCategoryRepository;

        //1. 하위역량 카테고리 등록
        public SubCompetencyCategory createSubCompetencyCategory(Long coreCategoryId, String subCategoryName, String subCategoryDescription) {
            CoreCompetencyCategory coreCompetencyCategory = coreCompetencyCategoryRepository.findById(coreCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 핵심역량 카테고리입니다."));

            SubCompetencyCategory subCompetencyCategory = SubCompetencyCategory.builder()
                    .subCategoryName(subCategoryName)
                    .subCategoryNote(subCategoryDescription)
                    .build();

            return subCompetencyCategoryRepository.save(subCompetencyCategory);

        }

        //2. 하위역량 카테고리 수정
        public SubCompetencyCategory updateSubCompetencyCategory(Long subCategoryId, String subCategoryName, String subCategoryDescription) {
            SubCompetencyCategory subCompetencyCategory = subCompetencyCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위 역량 카테고리입니다."));

            subCompetencyCategory.setSubCategoryName(subCategoryName);
            subCompetencyCategory.setSubCategoryNote(subCategoryDescription);

            return subCompetencyCategoryRepository.save(subCompetencyCategory);
        }

        //3. 하위역량 카테고리 삭제
        public void deleteSubCompetencyCategory(Long subCategoryId) {
            SubCompetencyCategory subCompetencyCategory = subCompetencyCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위 역량 카테고리입니다."));
            subCompetencyCategoryRepository.delete(subCompetencyCategory);
        }

        //4. 하위역량 카테고리 목록 전체 조회
        public List<SubCompetencyCategory> getAllSubCompetencyCategories() {
            return subCompetencyCategoryRepository.findAll();
        }

        //5. 하위역량 카테고리 상세 조회
        public SubCompetencyCategory getSubCompetencyCategoryById(Long subCategoryId) {
            return subCompetencyCategoryRepository.findById(subCategoryId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 하위 역량 카테고리입니다."));
        }

        //6. 카테고리 이름 중복 체크
        public boolean isSubCategoryNameDuplicate(Long coreCategoryId, String sub){
            List<SubCompetencyCategory> subCategories = subCompetencyCategoryRepository.findByCoreCategoryId(coreCategoryId);
            return subCategories.stream()
                    .anyMatch(subCategory -> subCategory.getSubCategoryName().equalsIgnoreCase(sub));
        }
}
