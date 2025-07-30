package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.entity.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminDiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/core-competency/diagnosis")
@RequiredArgsConstructor
public class AdminDiagnosisController {

    private final AdminDiagnosisService adminDiagnosisService;

    //1. 핵심 역량 진단 등록
    @PostMapping("/create")
    public ResponseEntity<CoreCompetencyAssessment> createCoreCompetencyAssessment(@RequestBody CoreCompetencyAssessmentDto coreCompetencyAssessmentDto) {
        return ResponseEntity.ok(adminDiagnosisService.createCoreCompetencyAssessment(coreCompetencyAssessmentDto));
    }

    //2. 핵심 역량 진단 수정
    @PutMapping("/update/{assessmentId}")
    public ResponseEntity<CoreCompetencyAssessment> updateCoreCompetencyAssessment(@PathVariable Long assessmentId, @RequestBody CoreCompetencyAssessmentDto coreCompetencyAssessmentDto) {
        return ResponseEntity.ok(adminDiagnosisService.updateCoreCompetencyAssessment(assessmentId, coreCompetencyAssessmentDto));
    }

    //3. 핵심 역량 진단 삭제
    @DeleteMapping("/delete/{assessmentId}")
    public ResponseEntity<Void> deleteCoreCompetencyAssessment(@PathVariable Long assessmentId) {
        adminDiagnosisService.deleteCoreCompetencyAssessment(assessmentId);
        return ResponseEntity.noContent().build();
    }

    //4. 핵심 역량 진단 상세 조회
    @GetMapping("/get/{assessmentId}")
    public ResponseEntity<CoreCompetencyAssessment> getCoreCompetencyAssessment(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(adminDiagnosisService.getCoreCompetencyAssessment(assessmentId));
    }

    //5. 핵심 역량 진단 전체 조회
    @GetMapping("/get/all")
    public ResponseEntity<List<CoreCompetencyAssessment>> getAllCoreCompetencyAssessments() {
        return ResponseEntity.ok(adminDiagnosisService.getAllCoreCompetencyAssessments());
    }


}
