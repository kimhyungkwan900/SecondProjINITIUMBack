package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryFormDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularCategoryController {

    private final ExtracurricularCategoryService extracurricularCategoryService;

    @PostMapping("/category")
    public ResponseEntity<String> insertCategory(@RequestBody ExtracurricularCategoryFormDTO dto) {
        extracurricularCategoryService.insertExtracurricularCategory(dto);
        return ResponseEntity.ok("분류 체계 등록 완료");
    }

    @PutMapping("/category")
    public ResponseEntity<String> updateCategory(@RequestParam Long ctgryId,
                                                 @RequestParam String useYn) {
        extracurricularCategoryService.updateExtracurricularCategory(ctgryId, useYn);
        return ResponseEntity.ok("분류 체계 사용 설정 변경 완료");
    }

    @DeleteMapping("/category")
    public ResponseEntity<String> deleteCategory(@RequestParam Long ctgryId) {
        extracurricularCategoryService.deleteExtracurricularCategory(ctgryId);
        return ResponseEntity.ok("분류 체계 삭제 완료");
    }

}
