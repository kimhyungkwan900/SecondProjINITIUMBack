package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminAssessmentSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/assessments/list")
public class AdminAssessmentSearchController {
    private final AdminAssessmentSearchService searchService;

    @GetMapping
    public ResponseEntity<List<CoreCompetencyAssessmentDto>> searchAssessments(
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String semesterCode,
            @RequestParam(required = false) String assessmentNo
    ) {
        List<CoreCompetencyAssessmentDto> results =
                searchService.assessmentList(academicYear, semesterCode, assessmentNo);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/result")
    public ResponseEntity<List<CoreCompetencyAssessmentDto>> searchAssessmentsResult(
            @RequestParam(required = false) String academicYear,
            @RequestParam(required = false) String semesterCode,
            @RequestParam(required = false) String assessmentNo
    ) {
        List<CoreCompetencyAssessmentDto> results =
                searchService.assessmentListResult(academicYear, semesterCode, assessmentNo);
        return ResponseEntity.ok(results);
    }
}
