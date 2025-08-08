package com.secondprojinitiumback.user.diagnostic.controller;

import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisRequestDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisResultDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalQuestionResponseDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalTestListDto;
import com.secondprojinitiumback.user.diagnostic.service.ExternalDiagnosisService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/external-diagnosis")
@RequiredArgsConstructor
public class ExternalDiagnosisController {

    private final ExternalDiagnosisService externalDiagnosisService;

    /** 🔍 외부 진단검사 전체 목록 조회 */
    @GetMapping("/tests")
    public ResponseEntity<List<ExternalTestListDto>> getExternalTestList() {
        return ResponseEntity.ok(externalDiagnosisService.getAvailableExternalTests());
    }

    /** 📜 특정 학생의 모든 외부 진단검사 결과 목록 조회 */
    @GetMapping("/results/{studentNo}")
    public ResponseEntity<List<ExternalDiagnosisResultDto>> getAllExternalResultsByStudent(@PathVariable String studentNo) {
        return ResponseEntity.ok(externalDiagnosisService.getAllResultsByStudent(studentNo));
    }

    /** 🔍 외부 진단검사 검색 */
    @GetMapping("/tests/search")
    public ResponseEntity<List<ExternalTestListDto>> searchExternalTests(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        return ResponseEntity.ok(externalDiagnosisService.searchExternalTestsByName(keyword));
    }

    /** 🔍 외부 진단검사 페이징 조회 */
    @GetMapping("/tests/paged")
    public ResponseEntity<Page<ExternalTestListDto>> getPagedExternalTests(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(externalDiagnosisService.getPagedExternalTests(keyword, pageable));
    }

    /** 📄 외부 문항 조회 - 원본 응답 (V1은 trgetSe 불필요) */
    @GetMapping("/questions")
    public ResponseEntity<Map<String, Object>> getExternalQuestionsRaw(
            @RequestParam("qestrnSeq")
            @Pattern(regexp = "^[0-9]+$", message = "qestrnSeq는 숫자여야 합니다.")
            String qestrnSeq
    ) {
        return ResponseEntity.ok(externalDiagnosisService.fetchExternalQuestions(qestrnSeq));
    }

    /** 📄 외부 문항 조회 - 파싱 DTO (V1은 trgetSe 불필요) */
    @GetMapping("/questions/parsed")
    public ResponseEntity<ExternalQuestionResponseDto> getExternalQuestionsParsed(
            @RequestParam("qestrnSeq")
            @Pattern(regexp = "^[0-9]+$", message = "qestrnSeq는 숫자여야 합니다.")
            String qestrnSeq
    ) {
        return ResponseEntity.ok(externalDiagnosisService.getParsedExternalQuestions(qestrnSeq));
    }

    /** ✅ 외부 검사 결과 제출 */
    @PostMapping("/submit")
    public ResponseEntity<ExternalDiagnosisResultDto> submitExternalDiagnosis(
            @RequestBody @Valid ExternalDiagnosisRequestDto dto
    ) {
        ExternalDiagnosisResultDto result = externalDiagnosisService.submitExternalResult(dto);
        // 201 Created + Location: 커리어넷 결과 URL
        return ResponseEntity
                .created(URI.create(result.getResultUrl()))
                .body(result);
    }
}
