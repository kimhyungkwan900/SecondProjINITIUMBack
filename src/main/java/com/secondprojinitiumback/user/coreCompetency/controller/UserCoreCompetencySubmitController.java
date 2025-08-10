package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencySubmitDto;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencySubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 👤 사용자 핵심역량 진단 응답 제출 컨트롤러
 *
 * 사용자가 응답을 제출할 때 호출되는 API 엔드포인트 제공
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/coreCompetency/submit")
public class UserCoreCompetencySubmitController {

    // 핵심역량 응답 제출 서비스
    private final UserCoreCompetencySubmitService userCoreCompetencySubmitService;

    /**
     * 📩 진단 응답 제출 API
     *
     * @param dto 사용자의 응답 데이터 (문항 ID, 선택지 라벨, 점수 등)
     * @param studentNo 응답 제출자(학생)의 학번
     * @return HTTP 200 OK 응답 (바디 없음)
     *
     * 예시 요청:
     * POST /api/user/coreCompetency/submit?studentNo=20241234
     * Body: {
     *   "assessmentId": 3,
     *   "responseItemList": [
     *     { "questionId": 10, "label": "매우 그렇다", "score": 5 },
     *     { "questionId": 11, "label": "그렇다", "score": 4 }
     *   ]
     * }
     */
    @PostMapping
    public ResponseEntity<Void> submitCoreCompetencyAssessment(
            @RequestBody UserCoreCompetencySubmitDto dto,
            @RequestParam("studentNo") String studentNo) {

        // 응답 저장 처리 위임
        userCoreCompetencySubmitService.submitResponses(dto, studentNo);

        // 처리 성공 시 200 OK 반환 (내용 없음)
        return ResponseEntity.ok().build();
    }
}
