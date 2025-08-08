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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;                // 진단검사 핵심 서비스
    private final DiagnosticResultRepository resultRepository;      // 결과 조회/저장 Repository
    private final DiagnosticResultDetailRepository resultDetailRepository; // 결과 상세 조회/저장 Repository
    private final DiagnosisScoreService scoreService;                // 점수 계산/해석 서비스
    private final PdfGenerationService pdfGenerationService;         // PDF 생성 서비스
    private final LoginInfoRepository loginInfoRepository;           // 로그인 정보 조회 Repository
    private final StudentRepository studentRepository;               // 학생 조회 Repository

    // 검사 목록 조회
    @GetMapping("/tests")
    public ResponseEntity<List<DiagnosticTestDto>> getAvailableTests() {
        return ResponseEntity.ok(diagnosisService.getAvailableTests());
    }

    // 검사 문항 조회
    @GetMapping("/{testId}/questions")
    public ResponseEntity<List<DiagnosticQuestionDto>> getQuestions(@PathVariable Long testId) {
        return ResponseEntity.ok(diagnosisService.getQuestionsByTestId(testId));
    }

    /**
     * 사용자 응답 제출
     * - 로그인 ID 추출 → LoginInfo 조회 → 서비스에 제출 요청
     */
    @PostMapping("/submit")
    public ResponseEntity<Map<String, Object>> submitDiagnosis(
            @RequestBody DiagnosisSubmitRequestDto dto
    ) {
        // 로그인 ID 직접 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        String loginId = null;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            loginId = userDetails.getUsername();
        }

        if (loginId == null) {
            throw new AccessDeniedException("로그인 정보를 가져올 수 없습니다.");
        }

        // LoginInfo 조회
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
     * - 로그인 사용자와 요청 studentNo가 동일해야 함
     */
    @GetMapping("/results/{studentNo}")
    public ResponseEntity<List<DiagnosticResultDto>> getAllResultsByStudent(@PathVariable String studentNo) {
        // 현재 로그인 사용자 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        String loginId = null;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            loginId = userDetails.getUsername();
        }

        if (loginId == null) {
            throw new AccessDeniedException("로그인 정보를 확인할 수 없습니다.");
        }

        // 로그인된 사용자의 studentNo 조회 (DB 연동 필요)
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
     * - 본인 소유 여부 검증 포함
     */
    @GetMapping("/result/{resultId}")
    public ResponseEntity<DiagnosticResultDto> getResult(@PathVariable Long resultId) {
        // 현재 로그인한 사용자 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        String loginId = null;

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            loginId = userDetails.getUsername(); // ✅ 로그인 ID 추출
        }

        if (loginId == null) {
            throw new AccessDeniedException("로그인 정보를 확인할 수 없습니다.");
        }

        // 서비스 호출 시 loginId 전달
        DiagnosticResultDto resultDto = diagnosisService.getResultWithStudentCheck(resultId, loginId);

        return ResponseEntity.ok(resultDto);
    }

    /**
     * 결과 상세 조회
     * - 각 문항별 선택 값과 점수 확인
     * - 본인 소유 여부 검증
     */
    @GetMapping("/result/{resultId}/details")
    public ResponseEntity<List<DiagnosticResultDetailDto>> getResultDetails(@PathVariable Long resultId) {
        // 1. 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("인증되지 않은 사용자입니다.");
        }

        String loginId = null;
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            loginId = userDetails.getUsername(); // 로그인 ID 추출
        }

        if (loginId == null) {
            throw new AccessDeniedException("로그인 정보를 확인할 수 없습니다.");
        }

        // 2. 검사 결과 조회
        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("검사 결과가 존재하지 않습니다."));

        // 3. 로그인한 사용자의 loginId와 검사 주인의 loginId 비교
        String resultOwnerLoginId = result.getStudent().getLoginInfo().getLoginId();
        if (!resultOwnerLoginId.equals(loginId)) {
            throw new AccessDeniedException("본인의 검사 결과만 조회할 수 있습니다.");
        }

        // 4. 결과 상세 조회
        List<DiagnosticResultDetail> details = resultDetailRepository.findByResultId(resultId);

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




    // 진단검사명 검색
    @GetMapping("/tests/search")
    public ResponseEntity<List<DiagnosticTestDto>> searchTests(@RequestParam String keyword) {
        return ResponseEntity.ok(diagnosisService.searchTestsByKeyword(keyword));
    }

    // 진단검사 페이징 조회
    @GetMapping("/tests/paged")
    public ResponseEntity<Page<DiagnosticTestDto>> getPagedTests(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return ResponseEntity.ok(diagnosisService.getPagedTests(keyword, pageable));
    }

    // PDF 결과 다운로드
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
