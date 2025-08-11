package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.IdealTalentProfileDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.CompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 페이지의 역량 카테고리(핵심/하위) 관련 API 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/admin/competencyCategory")
@RequiredArgsConstructor
public class AdminCompetencyCategoryController {

    private final AdminCompetencyCategoryService adminCompetencyCategoryService;

    /**
     * 등록된 모든 인재상 목록을 조회합니다.
     */
    @GetMapping("/ideal-talent-profiles")
    public ResponseEntity<List<IdealTalentProfileDto>> getIdealTalentProfiles() {
        return ResponseEntity.ok(adminCompetencyCategoryService.getAllIdealTalentProfiles());
    }


    // --- CUD (Create, Update, Delete) API ---

    /**
     * 새로운 역량 카테고리(핵심 또는 하위)를 생성합니다.
     */
    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.createCategory(competencyCategoryDto);
        return ResponseEntity.ok("등록 완료");
    }

    /**
     * 기존 역량 카테고리(핵심 또는 하위)를 수정합니다.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.updateCategory(id, competencyCategoryDto);
        return ResponseEntity.ok("수정 완료");
    }

    /**
     * 기존 역량 카테고리(핵심 또는 하위)를 삭제합니다.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, @RequestBody CompetencyCategoryDto competencyCategoryDto) {
        adminCompetencyCategoryService.deleteCategory(id, competencyCategoryDto);
        return ResponseEntity.ok("삭제 완료");
    }

    // --- Read API ---

    /**
     * 모든 핵심역량 카테고리 목록을 조회합니다.
     */
    @GetMapping("/core")
    public ResponseEntity<List<CoreCompetencyCategoryDto>> getAllCore() {
        List<CoreCompetencyCategory> categories = adminCompetencyCategoryService.getAllCoreCompetencyCategories();
        // 서비스로부터 받은 엔티티 리스트를 DTO 리스트로 변환하여 반환합니다.
        List<CoreCompetencyCategoryDto> dtoList = categories.stream()
                .map(CoreCompetencyCategoryDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * 모든 하위역량 카테고리 목록을 조회합니다.
     */
    @GetMapping("/sub")
    public ResponseEntity<List<SubCompetencyCategoryDto>> getAllSub() {
        List<SubCompetencyCategory> subCategories = adminCompetencyCategoryService.getAllSubCompetencyCategories();
        // 서비스로부터 받은 엔티티 리스트를 DTO 리스트로 변환하여 반환합니다.
        List<SubCompetencyCategoryDto> dtoList = subCategories.stream()
                .map(SubCompetencyCategoryDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * ID를 기준으로 특정 핵심역량 카테고리 하나를 조회합니다.
     */
    @GetMapping("/core/{id}")
    public ResponseEntity<CoreCompetencyCategoryDto> getCore(@PathVariable Long id) {
        CoreCompetencyCategory category = adminCompetencyCategoryService.getCoreCategory(id);
        // 서비스로부터 받은 엔티티를 DTO로 변환하여 반환합니다.
        return ResponseEntity.ok(CoreCompetencyCategoryDto.fromEntity(category));
    }

    /**
     * ID를 기준으로 특정 하위역량 카테고리 하나를 조회합니다.
     */
    @GetMapping("/sub/{id}")
    public ResponseEntity<SubCompetencyCategoryDto> getSub(@PathVariable Long id) {
        SubCompetencyCategory subCategory = adminCompetencyCategoryService.getSubCategory(id);
        // 서비스로부터 받은 엔티티를 DTO로 변환하여 반환합니다.
        return ResponseEntity.ok(SubCompetencyCategoryDto.fromEntity(subCategory));
    }

    // --- 중복 체크 API ---

    /**
     * 새로운 핵심역량 카테고리 이름의 중복 여부를 확인합니다.
     */
    @GetMapping("/check/core")
    public ResponseEntity<Boolean> checkCoreDuplicate(@RequestParam String name) {
        return ResponseEntity.ok(adminCompetencyCategoryService.isCoreCategoryNameDuplicate(name));
    }

    /**
     * 특정 핵심역량 내에서 하위역량 카테고리 이름의 중복 여부를 확인합니다.
     */
    @GetMapping("/check/sub")
    public ResponseEntity<Boolean> checkSubDuplicate(@RequestParam Long coreId, @RequestParam String name) {
        return ResponseEntity.ok(adminCompetencyCategoryService.isSubCategoryNameDuplicate(coreId, name));
    }
}