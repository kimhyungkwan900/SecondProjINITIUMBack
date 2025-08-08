package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCompetencyCategoryService;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminAssessmentCategoryMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/assessments")
@RequiredArgsConstructor
public class AssessmentCategoryMappingController {

    private final AdminAssessmentCategoryMappingService adminAssessmentCategoryMappingService;
    private final AdminCompetencyCategoryService adminCompetencyCategoryService;

    @GetMapping("/{assessmentId}/core")
    public ResponseEntity<List<CoreCompetencyCategoryDto>> getStruct(@PathVariable Long assessmentId) {
        List<CoreCompetencyCategoryDto> categoryStructure = adminAssessmentCategoryMappingService.getCategoryStructureByAssessment(assessmentId);
        return ResponseEntity.ok(categoryStructure);
    }


    // 핵심역량 ID로 매핑된 하위역량 리스트 조회
    @GetMapping("/{coreId}/subcategories")
    public ResponseEntity<List<SubCompetencyCategoryDto>> getSubCategoriesByCoreCategory(@PathVariable Long coreId) {
        List<SubCompetencyCategoryDto> dtoList = adminCompetencyCategoryService
                .getSubCategoriesByCoreId(coreId)
                .stream()
                .map(SubCompetencyCategoryDto::fromEntity)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

}
