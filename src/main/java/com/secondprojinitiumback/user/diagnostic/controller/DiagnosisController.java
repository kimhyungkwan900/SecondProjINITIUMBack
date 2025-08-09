package com.secondprojinitiumback.user.diagnostic.controller;

import com.secondprojinitiumback.common.security.Repository.LoginInfoRepository;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import com.secondprojinitiumback.user.diagnostic.dto.*;
import com.secondprojinitiumback.user.diagnostic.repository.DiagnosticResultDetailRepository;
import com.secondprojinitiumback.user.diagnostic.repository.DiagnosticResultRepository;
import com.secondprojinitiumback.user.diagnostic.service.DiagnosisScoreService;
import com.secondprojinitiumback.user.diagnostic.service.DiagnosisService;
import com.secondprojinitiumback.user.diagnostic.service.PdfGenerationService;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;                // 진단검사 핵심 서비스
    private final DiagnosticResultRepository resultRepository;      // 결과 조회/저장 Repository
    private final DiagnosticResultDetailRepository resultDetailRepository; // 결과 상세 조회/저장 Repository
    private final DiagnosisScoreService scoreService;               // 점수 계산/해석 서비스
    private final PdfGenerationService pdfGenerationService;        // PDF 생성 서비스
    private final LoginInfoRepository loginInfoRepository;          // 로그인 정보 조회 Repository
    private final StudentRepository studentRepository;              // 학생 조회 Repository

    // ==== 공통: 로그인 ID 추출 헬퍼 ====
    private String requireLoginId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        throw new AccessDeniedException("로그인 정보를 가져올 수 없습니다.");
    }

    // 검사 목록 조회 (서비스에서 delYn='N'만 반환)
    @GetMapping("/tests")
    public ResponseEntity<List<DiagnosticTestDto>> getAvailableTests() {
        return ResponseEntity.ok(diagnosisService.getAvailableTests());
    }

    // 검사 문항 조회 (삭제된 검사 차단은 서비스에서 처리)
    @GetMapping("/{testId}/questions")
    public ResponseEntity<List<DiagnosticQuestionDto>> getQuestions(@PathVariable Long testId) {
        return ResponseEntity.ok(diagnosisService.getQuestionsByTestId(testId));
    }

    /**
     * 사용자 응답 제출
     * - 로그인 ID → LoginInfo 조회 → 서비스에 제출
     * - 서비스에서 delYn='Y' 검사에 대한 제출은 차단
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitDiagnosis(@Valid @RequestBody DiagnosisSubmitRequestDto dto) {
        String loginId = requireLoginId();

        LoginInfo loginInfo = loginInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("해당 로그인 사용자를 찾을 수 없습니다."));

        Long resultId = diagnosisService.submitDiagnosis(dto, loginInfo);

        Map<String, Object> response = new HashMap<>();
        response.put("resultId", resultId);
        response.put("message", "응답이 저장되었습니다.");
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 학생의 모든 내부 진단검사 결과 조회
     * - 본인 studentNo만 허용
     * - 삭제된 검사 결과도 포함 (서비스에서 포함 반환)
     */
    @GetMapping("/results/{studentNo}")
    public ResponseEntity<List<DiagnosticResultDto>> getAllResultsByStudent(@PathVariable String studentNo) {
        String loginId = requireLoginId();

        Student student = studentRepository.findByLoginInfoLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

        if (!student.getStudentNo().equals(studentNo)) {
            throw new AccessDeniedException("본인의 검사 결과만 조회할 수 있습니다.");
        }

        List<DiagnosticResultDto> results = diagnosisService.getAllResultsByStudent(studentNo);
        return ResponseEntity.ok(results);
    }

    /**
     * 결과 요약 조회
     * - 삭제된 검사라도 소유자면 조회 가능
     */
    @GetMapping("/result/{resultId}")
    public ResponseEntity<DiagnosticResultDto> getResult(@PathVariable Long resultId) {
        String loginId = requireLoginId();
        DiagnosticResultDto resultDto = diagnosisService.getResultWithStudentCheck(resultId, loginId);
        return ResponseEntity.ok(resultDto);
    }

    /**
     * 결과 상세 조회
     * - 삭제된 검사라도 소유자면 조회 가능
     */
    @GetMapping("/result/{resultId}/details")
    public ResponseEntity<List<DiagnosticResultDetailDto>> getResultDetails(@PathVariable Long resultId) {
        String loginId = requireLoginId();

        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("검사 결과가 존재하지 않습니다."));

        // 소유자 검증: Result.loginInfo 우선, 없으면 Student.loginInfo
        String ownerLoginId = (result.getLoginInfo() != null)
                ? result.getLoginInfo().getLoginId()
                : (result.getStudent() != null && result.getStudent().getLoginInfo() != null
                ? result.getStudent().getLoginInfo().getLoginId()
                : null);

        if (!Objects.equals(ownerLoginId, loginId)) {
            throw new AccessDeniedException("본인의 검사 결과만 조회할 수 있습니다.");
        }

        // 상세 조회 (연관 경로 메서드명)
        List<DiagnosticResultDetail> details = resultDetailRepository.findByResult_Id(resultId);

        List<DiagnosticResultDetailDto> dtoList = details.stream()
                .map(d -> DiagnosticResultDetailDto.builder()
                        .questionId(d.getQuestion().getId())
                        .questionContent(d.getQuestion().getContent())
                        .selectedValue(d.getSelectedValue())
                        .score(d.getScore())
                        .build())
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    // 진단검사명 검색 (서비스에서 delYn='N'만)
    @GetMapping("/tests/search")
    public ResponseEntity<List<DiagnosticTestDto>> searchTests(@RequestParam String keyword) {
        return ResponseEntity.ok(diagnosisService.searchTestsByKeyword(keyword));
    }

    // 진단검사 페이징 조회 (서비스에서 delYn='N'만)
    @GetMapping("/tests/paged")
    public ResponseEntity<Page<DiagnosticTestDto>> getPagedTests(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(diagnosisService.getPagedTests(keyword, pageable));
    }

    // PDF 결과 다운로드 (삭제된 검사라도 소유자면 허용)
    @GetMapping("/result/{resultId}/pdf")
    public ResponseEntity<byte[]> downloadDiagnosisPdf(@PathVariable Long resultId) throws IOException {
        String loginId = requireLoginId();

        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("검사 결과 없음"));

        String ownerLoginId = (result.getLoginInfo() != null)
                ? result.getLoginInfo().getLoginId()
                : (result.getStudent() != null && result.getStudent().getLoginInfo() != null
                ? result.getStudent().getLoginInfo().getLoginId()
                : null);

        if (!Objects.equals(ownerLoginId, loginId)) {
            throw new AccessDeniedException("본인의 검사 결과만 조회할 수 있습니다.");
        }

        List<DiagnosticResultDetail> details = resultDetailRepository.findByResult_Id(resultId);
        String interpretation = scoreService.interpretScore(result.getTest().getId(), result.getTotalScore());

        byte[] pdfBytes = pdfGenerationService.generateDiagnosisResultPdf(result, details, interpretation);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline()
                .filename("diagnosis_result_" + resultId + ".pdf")
                .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    // 본인 결과 페이징 (삭제된 검사도 포함)
    @GetMapping("/results/{studentNo}/paged")
    public ResponseEntity<Page<DiagnosticResultDto>> getAllResultsByStudentPaged(
            @PathVariable String studentNo,
            @PageableDefault(size = 3, sort = "completionDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        String loginId = requireLoginId();

        Student student = studentRepository.findByLoginInfoLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

        if (!student.getStudentNo().equals(studentNo)) {
            throw new AccessDeniedException("본인의 검사 결과만 조회할 수 있습니다.");
        }

        Page<DiagnosticResultDto> page = diagnosisService.getPagedInternalResults(studentNo, pageable);
        return ResponseEntity.ok(page);
    }
}
