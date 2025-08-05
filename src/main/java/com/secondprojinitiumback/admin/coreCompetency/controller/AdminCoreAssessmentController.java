package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/admin/assessment")
@RequiredArgsConstructor
public class AdminCoreAssessmentController {

    private final AdminCoreAssessmentService diagnosisService;

    // 진단 등록
    @PostMapping("/create")
    public ResponseEntity<CoreCompetencyAssessment> createAssessment(@RequestBody CoreCompetencyAssessmentDto dto) {
        CoreCompetencyAssessment result = diagnosisService.createCoreCompetencyAssessment(dto);
        return ResponseEntity.ok(result);
    }

    // 진단 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<CoreCompetencyAssessment> updateAssessment(
            @PathVariable Long id,
            @RequestBody CoreCompetencyAssessmentDto dto
    ) {
        CoreCompetencyAssessment result = diagnosisService.updateCoreCompetencyAssessment(id, dto);
        return ResponseEntity.ok(result);
    }

    // 진단 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssessment(@PathVariable Long id) {
        diagnosisService.deleteCoreCompetencyAssessment(id);
        return ResponseEntity.ok("삭제 완료");
    }

    // 진단 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CoreCompetencyAssessment> getAssessment(@PathVariable Long id) {
        return ResponseEntity.ok(diagnosisService.getCoreCompetencyAssessment(id));
    }

    // 진단 전체 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<CoreCompetencyAssessment>> getAllAssessments() {
        return ResponseEntity.ok(diagnosisService.getAllCoreCompetencyAssessments());
    }
}
