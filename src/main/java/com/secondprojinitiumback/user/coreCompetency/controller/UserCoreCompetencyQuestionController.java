package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyQuestionDto;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/assessments")
public class UserCoreCompetencyQuestionController {

    // 사용자용 핵심역량 문항 서비스 의존성 주입
    private final UserCoreCompetencyQuestionService userCoreCompetencyQuestionService;

    /**
     * 특정 평가(assessmentId)에 해당하는 핵심역량 문항 + 선택지 리스트 조회 API

     * 요청 예시: GET /api/user/assessments/3/questions
     */
    @GetMapping("/{assessmentId}/questions")
    public ResponseEntity<List<UserCoreCompetencyQuestionDto>> getQuestions(@PathVariable Long assessmentId) {
        // 서비스에서 문항 DTO 리스트 받아와서 200 OK 응답으로 반환
        return ResponseEntity.ok(userCoreCompetencyQuestionService.getQuestionsByAssessment(assessmentId));
    }
}
