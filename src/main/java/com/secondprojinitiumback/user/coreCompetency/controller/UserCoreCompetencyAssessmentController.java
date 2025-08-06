package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyAssessmentDTO;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencyAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessments/list")
public class UserCoreCompetencyAssessmentController {

    private final UserCoreCompetencyAssessmentService assessmentService;

    @GetMapping
    public List<UserCoreCompetencyAssessmentDTO> getAllAssessment() {
        return assessmentService.getAllAssessment();
    }
}
