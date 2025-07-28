package com.secondprojinitiumback.user.diagnostic.controller;

import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisRequestDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisResultDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalQuestionResponseDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalTestListDto;
import com.secondprojinitiumback.user.diagnostic.service.ExternalDiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/external-diagnosis")
@RequiredArgsConstructor
public class ExternalDiagnosisController {

    private final ExternalDiagnosisService externalDiagnosisService;

    @Value("${career.api.key}")
    private String apiKey;

    @GetMapping("/tests")
    public ResponseEntity<List<ExternalTestListDto>> getExternalTestList() {
        return ResponseEntity.ok(externalDiagnosisService.getAvailableExternalTests());
    }

    @GetMapping("/tests/search")
    public ResponseEntity<List<ExternalTestListDto>> searchExternalTests(
            @RequestParam("keyword") String keyword
    ) {
        return ResponseEntity.ok(externalDiagnosisService.searchExternalTestsByName(keyword));
    }


    @GetMapping("/tests/paged")
    public ResponseEntity<Page<ExternalTestListDto>> getPagedExternalTests(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(externalDiagnosisService.getPagedExternalTests(keyword, pageable));
    }


    /**
     * 1. 외부 문항 조회 - 원본 응답 Map 그대로 반환
     * 예: /api/external-diagnosis/questions?qestrnSeq=CT202407&trgetSe=student
     */
    @GetMapping("/questions")
    public ResponseEntity<Map<String, Object>> getExternalQuestionsRaw(
            @RequestParam("qestrnSeq") String qestrnSeq,
            @RequestParam("trgetSe") String trgetSe
    ) {
        Map<String, Object> questions = externalDiagnosisService.fetchExternalQuestions(qestrnSeq, trgetSe, apiKey);
        return ResponseEntity.ok(questions);
    }

    /**
     * 2. 외부 문항 조회 - 파싱된 응답 DTO 반환
     * 예: /api/external-diagnosis/questions/parsed?qestrnSeq=CT202407&trgetSe=student
     */
    @GetMapping("/questions/parsed")
    public ResponseEntity<ExternalQuestionResponseDto> getExternalQuestionsParsed(
            @RequestParam("qestrnSeq") String qestrnSeq,
            @RequestParam("trgetSe") String trgetSe
    ) {
        ExternalQuestionResponseDto dto =
                externalDiagnosisService.getParsedExternalQuestions(qestrnSeq, trgetSe, apiKey);
        return ResponseEntity.ok(dto);
    }

    /**
     * 3. 외부 검사 결과 제출
     * 예: POST /api/external-diagnosis/submit
     */
    @PostMapping("/submit")
    public ResponseEntity<ExternalDiagnosisResultDto> submitExternalDiagnosis(
            @RequestBody ExternalDiagnosisRequestDto dto
    ) {
        ExternalDiagnosisResultDto resultDto = externalDiagnosisService.submitExternalResult(dto, apiKey);
        return ResponseEntity.ok(resultDto);
    }
}
