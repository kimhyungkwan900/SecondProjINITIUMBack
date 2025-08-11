package com.secondprojinitiumback.user.coreCompetency.controller;

import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencySubmitDto;
import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencySubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  사용자 핵심역량 진단 응답 제출 컨트롤러
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
     *  진단 응답 제출 API
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
