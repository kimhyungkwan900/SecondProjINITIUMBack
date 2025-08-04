package com.secondprojinitiumback.user.diagnostic.controller;

import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisRequestDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisResultDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalQuestionResponseDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalTestListDto;
import com.secondprojinitiumback.user.diagnostic.service.ExternalDiagnosisService;
import jakarta.validation.Valid;
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

    /**
     * 🔍 외부 진단검사 전체 목록 조회
     */
    @GetMapping("/tests")
    public ResponseEntity<List<ExternalTestListDto>> getExternalTestList() {
        return ResponseEntity.ok(externalDiagnosisService.getAvailableExternalTests());
    }

    /**
     * 📜 특정 학생의 모든 외부 진단검사 결과 목록 조회
     */
    @GetMapping("/results/{studentNo}")
    public ResponseEntity<List<ExternalDiagnosisResultDto>> getAllExternalResultsByStudent(@PathVariable String studentNo) {
        return ResponseEntity.ok(externalDiagnosisService.getAllResultsByStudent(studentNo));
    }

    /**
     * 🔍 외부 진단검사 검색
     */
    @GetMapping("/tests/search")
    public ResponseEntity<List<ExternalTestListDto>> searchExternalTests(
            @RequestParam("keyword") String keyword
    ) {
        return ResponseEntity.ok(externalDiagnosisService.searchExternalTestsByName(keyword));
    }

    /**
     * 🔍 외부 진단검사 페이징 조회
     */
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
     * 📄 외부 문항 조회 - 원본 응답 (Map 그대로 반환)
     * 🔹 V1은 trgetSe 불필요
     */
    @GetMapping("/questions")
    public ResponseEntity<Map<String, Object>> getExternalQuestionsRaw(
            @RequestParam("qestrnSeq") String qestrnSeq
    ) {
        return ResponseEntity.ok(externalDiagnosisService.fetchExternalQuestions(qestrnSeq));
    }

    /**
     * 📄 외부 문항 조회 - 파싱된 응답 DTO 반환
     * 🔹 V1은 trgetSe 불필요
     */
    @GetMapping("/questions/parsed")
    public ResponseEntity<ExternalQuestionResponseDto> getExternalQuestionsParsed(
            @RequestParam("qestrnSeq") String qestrnSeq
    ) {
        return ResponseEntity.ok(externalDiagnosisService.getParsedExternalQuestions(qestrnSeq));
    }

    /**
     * ✅ 외부 검사 결과 제출
     * 🔹 studentNo, answers 등 DTO에 포함
     */
    @PostMapping("/submit")
    public ResponseEntity<ExternalDiagnosisResultDto> submitExternalDiagnosis(
            @Valid @RequestBody ExternalDiagnosisRequestDto dto
    ) {
        return ResponseEntity.ok(externalDiagnosisService.submitExternalResult(dto));
    }
}