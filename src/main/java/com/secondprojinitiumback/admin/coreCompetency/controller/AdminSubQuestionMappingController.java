package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.AdminSubQuestionMappingDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminSubQuestionMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/{assessmentId}")
@RequiredArgsConstructor
public class AdminSubQuestionMappingController {

    private final AdminSubQuestionMappingService adminSubQuestionMappingService;
    private final AdminCompetencyCategoryService adminCompetencyCategoryService;


    /**
     * 진단에 매핑된 하위역량 목록 조회
     */
    @GetMapping("/subcategories")
    public ResponseEntity<List<SubCompetencyCategoryDto>> getSubCategoriesByAssessmentId(
            @PathVariable Long assessmentId) {

        // 서비스로부터 하위역량 엔티티 리스트 조회
        List<SubCompetencyCategory> subCategories = adminCompetencyCategoryService
                .getSubCategoriesByAssessmentId(assessmentId);

        // DTO 변환
        List<SubCompetencyCategoryDto> dtoList = subCategories.stream()
                .map(SubCompetencyCategoryDto::fromEntity2) // 평가 ID는 엔티티 내부에서 추출
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    /**
     * 특정 하위역량에 매핑된 문항 목록 및 선택지 개수를 조회
     */
    @GetMapping("/{subCategoryId}/questions")
    public AdminSubQuestionMappingDto getMappedQuestionsBySubCategory(@PathVariable Long subCategoryId) {
        return adminSubQuestionMappingService.getQuestionsBySubCompetency(subCategoryId);
    }
}
