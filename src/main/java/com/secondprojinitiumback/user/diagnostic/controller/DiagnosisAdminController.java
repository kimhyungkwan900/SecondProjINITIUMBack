package com.secondprojinitiumback.user.diagnostic.controller;

import com.secondprojinitiumback.user.diagnostic.dto.DiagnosticTestDto;
import com.secondprojinitiumback.user.diagnostic.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/diagnosis")
@RequiredArgsConstructor
public class DiagnosisAdminController {

    private final DiagnosisService diagnosisService;

    /**
     * 모든 진단검사 목록 조회
     * - 관리자용: 전체 검사 데이터 반환
     */
    @GetMapping("/tests")
    public ResponseEntity<List<DiagnosticTestDto>> getAllTests() {
        return ResponseEntity.ok(diagnosisService.getAvailableTests());
    }


    /**
     * 진단검사 생성
     * - 요청 본문(DTO)을 받아 서비스에 전달하여 저장
     * - 생성된 검사 ID와 메시지를 JSON 형태로 반환
     */
    @PostMapping("/tests")
    public ResponseEntity<Map<String, Object>> createTest(@RequestBody DiagnosticTestDto dto) {
        Long createdId = diagnosisService.registerDiagnosticTest(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("testId", createdId);
        response.put("message", "검사가 성공적으로 등록되었습니다.");
        return ResponseEntity.ok(response);
    }

    /**
     * 진단검사 삭제
     * - PathVariable로 검사 ID를 받아 서비스에 삭제 요청
     * - 삭제 성공 시 204 No Content 응답
     */
    @DeleteMapping("/tests/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        diagnosisService.deleteDiagnosticTest(id);
        return ResponseEntity.noContent().build();
    }
}

