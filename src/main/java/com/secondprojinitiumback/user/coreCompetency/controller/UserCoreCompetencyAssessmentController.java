package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyAssessmentDTO;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencyAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assessments/list")
public class UserCoreCompetencyAssessmentController {

    // 핵심역량 진단 평가 목록을 제공하는 서비스 의존성 주입
    private final UserCoreCompetencyAssessmentService assessmentService;

    /**
     * 사용자용 전체 핵심역량 평가 목록 조회 API
     * 요청 예시: GET /api/assessments/list
     */
    @GetMapping
    public List<UserCoreCompetencyAssessmentDTO> getAllAssessment() {
        // 서비스에서 평가 목록을 가져와서 그대로 반환 (자동으로 JSON 변환됨)
        return assessmentService.getAllAssessment();
    }
}
