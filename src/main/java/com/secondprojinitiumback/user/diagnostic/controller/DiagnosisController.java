package com.secondprojinitiumback.user.diagnostic.controller;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import com.secondprojinitiumback.user.diagnostic.dto.DiagnosisSubmitRequestDto;
import com.secondprojinitiumback.user.diagnostic.dto.DiagnosticQuestionDto;
import com.secondprojinitiumback.user.diagnostic.dto.DiagnosticResultDto;
import com.secondprojinitiumback.user.diagnostic.dto.DiagnosticTestDto;
import com.secondprojinitiumback.user.diagnostic.repository.DiagnosticResultDetailRepository;
import com.secondprojinitiumback.user.diagnostic.repository.DiagnosticResultRepository;
import com.secondprojinitiumback.user.diagnostic.service.DiagnosisScoreService;
import com.secondprojinitiumback.user.diagnostic.service.DiagnosisService;
import com.secondprojinitiumback.user.diagnostic.service.PdfGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;
    private final DiagnosticResultRepository resultRepository;
    private final DiagnosticResultDetailRepository resultDetailRepository;
    private final DiagnosisScoreService scoreService;
    private final PdfGenerationService pdfGenerationService;

    // 🔍 검사 목록 조회
    @GetMapping("/tests")
    public ResponseEntity<List<DiagnosticTestDto>> getAvailableTests() {
        return ResponseEntity.ok(diagnosisService.getAvailableTests());
    }

    // 📄 검사 문항 조회
    @GetMapping("/{testId}/questions")
    public ResponseEntity<List<DiagnosticQuestionDto>> getQuestions(@PathVariable Long testId) {
        return ResponseEntity.ok(diagnosisService.getQuestionsByTestId(testId));
    }

    // ✅ 사용자 응답 제출
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitDiagnosis(@RequestBody DiagnosisSubmitRequestDto dto) {
        Long resultId = diagnosisService.submitDiagnosis(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("resultId", resultId);
        response.put("message", "응답이 저장되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 📊 결과 요약 조회
    @GetMapping("/result/{resultId}")
    public ResponseEntity<DiagnosticResultDto> getResult(@PathVariable Long resultId) {
        return ResponseEntity.ok(diagnosisService.getResultSummary(resultId));
    }

    // 진단검사명으로 검색
    @GetMapping("/tests/search")
    public ResponseEntity<List<DiagnosticTestDto>> searchTests(@RequestParam String keyword) {
        return ResponseEntity.ok(diagnosisService.searchTestsByKeyword(keyword));
    }

    @GetMapping("/tests/paged")
    public ResponseEntity<Page<DiagnosticTestDto>> getPagedTests(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(diagnosisService.getPagedTests(keyword, pageable));
    }

    @PostMapping("/tests")
    public ResponseEntity<Map<String, Object>> createTest(@RequestBody DiagnosticTestDto dto) {
        Long createdId = diagnosisService.registerDiagnosticTest(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("testId", createdId);
        response.put("message", "검사가 성공적으로 등록되었습니다.");
        return ResponseEntity.ok(response);
    }



    @GetMapping("/result/{resultId}/pdf")
    public ResponseEntity<byte[]> downloadDiagnosisPdf(@PathVariable Long resultId) throws IOException {
        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("검사 결과 없음"));

        List<DiagnosticResultDetail> details = resultDetailRepository.findByResultId(resultId);
        String interpretation = scoreService.interpretScore(result.getTest().getId(), result.getTotalScore());

        byte[] pdfBytes = pdfGenerationService.generateDiagnosisResultPdf(result, details, interpretation);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("diagnosis_result_" + resultId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}