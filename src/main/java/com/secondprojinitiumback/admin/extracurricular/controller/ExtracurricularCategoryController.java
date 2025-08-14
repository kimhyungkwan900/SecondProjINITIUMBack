package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyCategoryDto;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularCategoryFormDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.SchoolSubjectDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularCategoryService;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.user.employee.domain.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularCategoryController {

    private final ExtracurricularCategoryService extracurricularCategoryService;

    @PostMapping("/category")
    public ResponseEntity<String> insertCategory(@RequestBody ExtracurricularCategoryFormDTO dto) {
        System.out.println("dto" + dto.getSubjectCode());
        extracurricularCategoryService.insertExtracurricularCategory(dto);
        return ResponseEntity.ok("분류 체계 등록 완료");
    }

    @PutMapping("/category")
    public ResponseEntity<String> updateCategory(@RequestBody ExtracurricularCategoryFormDTO dto) {
        extracurricularCategoryService.updateExtracurricularCategory(dto);
        return ResponseEntity.ok("분류 체계 사용 설정 변경 완료");
    }

    @DeleteMapping("/category")
    public ResponseEntity<String> deleteCategory(@RequestParam Long ctgryId) {
        extracurricularCategoryService.deleteExtracurricularCategory(ctgryId);
        return ResponseEntity.ok("분류 체계 삭제 완료");
    }

//
//    @GetMapping("/category")
//    public ResponseEntity<?> getCategory(@RequestParam Long ctgryId) {
//        List<ExtracurricularCategoryDTO> list = extracurricularCategoryService.getExtracurricularCategoryList(ctgryId);
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("/category")
    public ResponseEntity<List<ExtracurricularCategoryDTO>> getCategories(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String programName,
            @RequestParam(required = false) Long competencyId,
            @RequestParam(required = false) String departmentCode) {

        List<ExtracurricularCategoryDTO> list;

        if (categoryId != null) {
            // 1) 카테고리 ID 우선 조회
            list = extracurricularCategoryService.findByCategoryId(categoryId);
        } else {
            // 2) 필터 조건 조회
            list = extracurricularCategoryService.findByFilters(programName, competencyId, departmentCode);
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/category/employees")
    public ResponseEntity<List<SchoolSubjectDTO>> getEmployees() {
        List<SchoolSubject> list = extracurricularCategoryService.findAllSchoolSubject();
        List<SchoolSubjectDTO> dtoList = list.stream()
                .map(subject -> new SchoolSubjectDTO(subject.getSubjectCode(), subject.getSubjectName()))
        .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/category/list")
    public ResponseEntity<List<ExtracurricularCategoryDTO>> getCategoryList(@RequestParam String empId){
        List<ExtracurricularCategoryDTO> list = extracurricularCategoryService.findByEmpNo(empId);
        return ResponseEntity.ok(list);
    }

    // 핵심역량 받아오기
    @GetMapping("/core/category")
    public ResponseEntity<List<CoreCompetencyCategoryDto>> getCoreCategoryList() {
        List<CoreCompetencyCategoryDto> list = extracurricularCategoryService.findAllCoreCategory();
        return ResponseEntity.ok(list);
    }

    // 핵심역량 하위 역량 받아오기
    @GetMapping("/sub/category")
    public ResponseEntity<List<SubCompetencyCategoryDto>> getSubCategoryList(
            @RequestParam Long id
    ) {
        List<SubCompetencyCategoryDto> list = extracurricularCategoryService.findSubCategory(id);
        return ResponseEntity.ok(list);
    }
}
