package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyQuestionDto;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/assessments")
public class UserCoreCompetencyQuestionController {

    private final UserCoreCompetencyQuestionService userCoreCompetencyQuestionService;

    @GetMapping("/{assessmentId}/questions")
    public ResponseEntity<List<UserCoreCompetencyQuestionDto>> getQuestions(@PathVariable Long assessmentId) {
        return ResponseEntity.ok(userCoreCompetencyQuestionService.getQuestionsByAssessment(assessmentId));
    }
}
