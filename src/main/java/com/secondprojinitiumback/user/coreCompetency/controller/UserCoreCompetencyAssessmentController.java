package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyAssessmentDTO;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencyAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // 로그인 사용자(학생)가 응답한 진단 ID 목록
    @GetMapping("/assessments/responded/{studentNo}")
    public List<Long> getMyRespondedAssessmentIds(@PathVariable String studentNo) {
        return assessmentService.getRespondedAssessmentIds(studentNo);
    }

    // 특정 진단에 대한 내 응답 존재 여부 조회
     @GetMapping("/assessments/{assessmentId}/responded/{studentNo}")
    public Map<String, Boolean> hasResponded(@PathVariable Long assessmentId, @PathVariable String studentNo) {
        boolean responded = assessmentService.hasStudentResponded(assessmentId, studentNo);
        return Map.of("responded", responded);
    }

    @GetMapping("/assessments/responded-details/{studentNo}")
    public List<UserCoreCompetencyAssessmentDTO> getMyRespondedAssessmentDetails(@PathVariable String studentNo) {
        return assessmentService.getRespondedAssessment(studentNo);
    }
}
