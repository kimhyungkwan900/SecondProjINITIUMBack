package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.entity.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCategoryService;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminSubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/competency-category")
@RequiredArgsConstructor
public class AdminCompetencyCategoryController {

    private final AdminCoreCategoryService coreCategoryService;
    private final AdminSubCategoryService subCategoryService;

    //1. 핵심역량 카테고리 등록
    @PostMapping("/create/core-category")
    public ResponseEntity<CoreCompetencyCategory> createCoreCompetencyCategory(@RequestParam String name, @RequestBody String description) {
        return ResponseEntity.ok(coreCategoryService.createCoreCategory(name, description));
    }

    //2. 핵심역량 카테고리 수정
    @PutMapping("/update/core-category/{id}")
    public ResponseEntity<CoreCompetencyCategory> updateCoreCompetencyCategory(@PathVariable Long id, @RequestBody String name, @RequestBody String description) {
        return ResponseEntity.ok(coreCategoryService.updateCoreCategory(id, name, description));
    }

    //3. 핵심역량 카테고리 삭제
    @DeleteMapping("/delete/core-category/{id}")
    public ResponseEntity<Void> deleteCoreCompetencyCategory(@PathVariable Long id) {
        coreCategoryService.deleteCoreCategory(id);
        return ResponseEntity.noContent().build();
    }

    //4. 핵심역량 카테고리 상세 조회
    @GetMapping("/get/core-category/{id}")
    public ResponseEntity<CoreCompetencyCategory> getCoreCompetencyCategory(@PathVariable Long id) {
        return ResponseEntity.ok(coreCategoryService.getCoreCategory(id));
    }

    //5. 핵심역량 카테고리 전체 조회
    @GetMapping("/get/all-core-categories")
    public ResponseEntity<List<CoreCompetencyCategory>> getAllCoreCompetencyCategories() {
        return ResponseEntity.ok(coreCategoryService.getAllCoreCategories());
    }

    //6. 하위역량 카테고리 등록
    @PostMapping("/create/sub-category")
    public ResponseEntity<SubCompetencyCategory> createSubCompetencyCategory(@RequestBody Long coreCategoryId, @RequestBody String subCategoryName, @RequestBody String subCategoryDescription) {
        return ResponseEntity.ok(subCategoryService.createSubCompetencyCategory(coreCategoryId, subCategoryName, subCategoryDescription));
    }

    //7. 하위역량 카테고리 수정
    @PutMapping("/update/sub-category/{subCategoryId}")
    public ResponseEntity<SubCompetencyCategory> updateSubCompetencyCategory(@PathVariable Long subCategoryId, @RequestBody String subCategoryName, @RequestBody String subCategoryDescription) {
        return ResponseEntity.ok(subCategoryService.updateSubCompetencyCategory(subCategoryId, subCategoryName, subCategoryDescription));
    }

    //8. 하위역량 카테고리 삭제
    @DeleteMapping("/delete/sub-category/{subCategoryId}")
    public ResponseEntity<Void> deleteSubCompetencyCategory(@PathVariable Long subCategoryId) {
        subCategoryService.deleteSubCompetencyCategory(subCategoryId);
        return ResponseEntity.noContent().build();
    }

    //9. 하위역량 카테고리 목록 전체 조회
    @GetMapping("/get/all-sub-categories")
    public ResponseEntity<List<SubCompetencyCategory>> getAllSubCompetencyCategories() {
        return ResponseEntity.ok(subCategoryService.getAllSubCompetencyCategories());
    }

    //10. 하위역량 카테고리 상세 조회
    @GetMapping("/get/sub-category/{subCategoryId}")
    public ResponseEntity<SubCompetencyCategory> getSubCompetencyCategoryById(@PathVariable Long subCategoryId) {
        return ResponseEntity.ok(subCategoryService.getSubCompetencyCategoryById(subCategoryId));
    }

    //11. 핵심 카테고리 이름 중복 체크
    @GetMapping("/check/core-category-name-duplicate")
    public ResponseEntity<Boolean> isCoreCategoryNameDuplicate(@RequestBody String name) {
        return ResponseEntity.ok(coreCategoryService.isCoreCategoryNameDuplicate(name));
    }

    //12. 하위 카테고리 이름 중복 체크
    @GetMapping("/check/sub-category-name-duplicate")
    public ResponseEntity<Boolean> isSubCategoryNameDuplicate(@RequestBody Long coreCategoryId, @RequestBody String subCategoryName) {
        return ResponseEntity.ok(subCategoryService.isSubCategoryNameDuplicate(coreCategoryId, subCategoryName));
    }

}
