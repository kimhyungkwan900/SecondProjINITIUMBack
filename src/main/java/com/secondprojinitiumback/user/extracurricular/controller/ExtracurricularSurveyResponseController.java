package com.secondprojinitiumback.user.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularSurveyResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/extracurricular/survey")
@RequiredArgsConstructor
public class ExtracurricularSurveyResponseController {

    private final ExtracurricularSurveyResponseService extracurricularSurveyResponseService;

    // 비교과 프로그램 설문조사 조회
    @GetMapping("/program")
    public ResponseEntity<?> getSurveyByProgram(
            @RequestParam("eduMngId") Long eduMngId
    ){
        ExtracurricularSurveyDTO survey = extracurricularSurveyResponseService.getSurvey(eduMngId);
        return ResponseEntity.ok(survey);
    }

    // 비교과 프로그램 설문 응답 저장
    @PostMapping("/response")
    public ResponseEntity<?> saveSurveyResponse(
            @RequestParam("srvyId") Long srvyId,
            @RequestBody ExtracurricularSurveyResponseDTO responseDTO
    ) {
        extracurricularSurveyResponseService.saveSurveyResponse(srvyId, responseDTO);
        return ResponseEntity.ok("설문 응답이 저장되었습니다."
        );
    }


}
