package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 컨트롤러임을 명시
@RequestMapping("/api/admin/competencyCategory") // 기본 요청 경로 설정
@RequiredArgsConstructor // 생성자 주입을 위한 롬복 어노테이션
public class AdminCompetencyCategoryController {

    private final AdminCompetencyCategoryService adminCompetencyCategoryService;

    // 핵심역량 또는 하위역량 카테고리 등록 API
    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.createCategory(competencyCategoryDto);
        return ResponseEntity.ok("등록 완료");
    }

    // 핵심역량 또는 하위역량 카테고리 수정 API
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.updateCategory(id, competencyCategoryDto);
        return ResponseEntity.ok("수정 완료");
    }

    // 핵심역량 또는 하위역량 카테고리 삭제 API
    @DeleteMapping("/delete/{levelType}/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable String levelType, @PathVariable Long id) {
        adminCompetencyCategoryService.deleteCategory(levelType, id);
        return ResponseEntity.ok("삭제 완료");
    }

    // 전체 핵심역량 카테고리 목록 조회 API
    @GetMapping("/core")
    public ResponseEntity<List<CoreCompetencyCategory>> getAllCore() {
        return ResponseEntity.ok(adminCompetencyCategoryService.getAllCoreCompetencyCategories());
    }

    // 전체 하위역량 카테고리 목록 조회 API
    @GetMapping("/sub")
    public ResponseEntity<List<SubCompetencyCategory>> getAllSub() {
        return ResponseEntity.ok(adminCompetencyCategoryService.getAllSubCompetencyCategories());
    }

    // 단일 핵심역량 카테고리 조회 API
    @GetMapping("/core/{id}")
    public ResponseEntity<CoreCompetencyCategory> getCore(@PathVariable Long id) {
        return ResponseEntity.ok(adminCompetencyCategoryService.getCoreCategory(id));
    }

    // 단일 하위역량 카테고리 조회 API
    @GetMapping("/sub/{id}")
    public ResponseEntity<SubCompetencyCategory> getSub(@PathVariable Long id) {
        return ResponseEntity.ok(adminCompetencyCategoryService.getSubCategory(id));
    }

    // 핵심역량 이름 중복 여부 확인 API
    @GetMapping("/check/core")
    public ResponseEntity<Boolean> checkCoreDuplicate(@RequestParam String name) {
        return ResponseEntity.ok(adminCompetencyCategoryService.isCoreCategoryNameDuplicate(name));
    }

    // 하위역량 이름 중복 여부 확인 API
    @GetMapping("/check/sub")
    public ResponseEntity<Boolean> checkSubDuplicate(@RequestParam Long coreId, @RequestParam String name) {
        return ResponseEntity.ok(adminCompetencyCategoryService.isSubCategoryNameDuplicate(coreId, name));
    }
}

