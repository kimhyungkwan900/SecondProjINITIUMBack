package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryFormDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularCateGoryController {

    private final ExtracurricularCategoryService extracurricularCategoryService;

    // 분류 체계 등록
    @PostMapping("/category")
    public void insertCategory(@RequestBody ExtracurricularCategoryFormDTO dto) {
        extracurricularCategoryService.insertExtracurricularCategory(dto);
    }

    // 분류 체계 사용 설정
    @PutMapping("/cateGory")
    public void updateCateGory(@RequestParam("ctgryId") Long ctgryId,
                               @RequestParam("useYn") String useYn) {
        extracurricularCategoryService.updateExtracurricularCategory(ctgryId, useYn);
    }

    // 분류 체계 삭제

}
