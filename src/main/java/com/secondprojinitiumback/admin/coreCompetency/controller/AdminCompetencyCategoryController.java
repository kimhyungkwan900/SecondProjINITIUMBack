package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/competencyCategory")
@RequiredArgsConstructor
public class AdminCompetencyCategoryController {

    private final AdminCompetencyCategoryService adminCompetencyCategoryService;

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.createCategory(competencyCategoryDto);
        return ResponseEntity.ok("등록 완료");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.updateCategory(id, competencyCategoryDto);
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/delete/{levelType}/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String levelType, @PathVariable Long id) {
        adminCompetencyCategoryService.deleteCategory(levelType, id);
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/core")
    public ResponseEntity<List<CoreCompetencyCategory>> getAllCore() {
        return ResponseEntity.ok(adminCompetencyCategoryService.getAllCoreCompetencyCategories());
    }

    @GetMapping("/sub")
    public ResponseEntity<List<SubCompetencyCategory>> getAllSub() {
        return ResponseEntity.ok(adminCompetencyCategoryService.getAllSubCompetencyCategories());
    }

    @GetMapping("/core/{id}")
    public ResponseEntity<CoreCompetencyCategory> getCore(@PathVariable Long id) {
        return ResponseEntity.ok(adminCompetencyCategoryService.getCoreCategory(id));
    }

    @GetMapping("/sub/{id}")
    public ResponseEntity<SubCompetencyCategory> getSub(@PathVariable Long id) {
        return ResponseEntity.ok(adminCompetencyCategoryService.getSubCategory(id));
    }

    @GetMapping("/check/core")
    public ResponseEntity<Boolean> checkCoreDuplicate(@RequestParam String name) {
        return ResponseEntity.ok(adminCompetencyCategoryService.isCoreCategoryNameDuplicate(name));
    }

    @GetMapping("/check/sub")
    public ResponseEntity<Boolean> checkSubDuplicate(@RequestParam Long coreId, @RequestParam String name) {
        return ResponseEntity.ok(adminCompetencyCategoryService.isSubCategoryNameDuplicate(coreId, name));
    }
}
