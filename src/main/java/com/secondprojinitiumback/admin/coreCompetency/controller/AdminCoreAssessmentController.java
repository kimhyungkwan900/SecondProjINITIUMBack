package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.AssessmentListResponseDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyAssessmentDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreAssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 페이지의 핵심역량 진단 문항 관련 CRUD API 요청을 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/api/admin/assessment")
@RequiredArgsConstructor
public class AdminCoreAssessmentController {

    private final AdminCoreAssessmentService diagnosisService;


    /**
     * 새로운 핵심역량 진단 문항을 생성합니다.
     */
    @PostMapping("/create")
    public ResponseEntity<CoreCompetencyAssessmentDto> createAssessment(@RequestBody CoreCompetencyAssessmentDto dto) {
        CoreCompetencyAssessment result = diagnosisService.createCoreCompetencyAssessment(dto);
        // 서비스 계층에서 반환된 엔티티를 DTO로 변환하여 클라이언트에 응답합니다.
        return ResponseEntity.ok(CoreCompetencyAssessmentDto.fromEntity(result));
    }

    /**
     * 기존의 핵심역량 진단 문항을 수정합니다.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<CoreCompetencyAssessmentDto> updateAssessment(
            @PathVariable Long id,
            @RequestBody CoreCompetencyAssessmentDto dto
    ) {
        CoreCompetencyAssessment result = diagnosisService.updateCoreCompetencyAssessment(id, dto);
        // 서비스 계층에서 반환된 엔티티를 DTO로 변환하여 클라이언트에 응답합니다.
        return ResponseEntity.ok(CoreCompetencyAssessmentDto.fromEntity(result));
    }

    /**
     * 특정 핵심역량 진단 문항을 삭제합니다.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssessment(@PathVariable Long id) {
        diagnosisService.deleteCoreCompetencyAssessment(id);
        return ResponseEntity.ok("삭제 완료");
    }

    /**
     * ID를 기준으로 특정 핵심역량 진단 문항 하나를 조회합니다.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CoreCompetencyAssessmentDto> getAssessment(@PathVariable Long id) {
        CoreCompetencyAssessment result = diagnosisService.getCoreCompetencyAssessment(id);
        // 서비스 계층에서 반환된 엔티티를 DTO로 변환하여 클라이언트에 응답합니다.
        return ResponseEntity.ok(CoreCompetencyAssessmentDto.fromEntity(result));
    }

    /**
     * 모든 핵심역량 진단 문항 목록을 조회합니다.
     */
    @GetMapping("/all")
    public ResponseEntity<List<CoreCompetencyAssessmentDto>> getAllAssessments() {
        List<CoreCompetencyAssessment> assessments = diagnosisService.getAllCoreCompetencyAssessments();
        // 서비스 계층에서 받은 엔티티 리스트를 DTO 리스트로 변환합니다.
        List<CoreCompetencyAssessmentDto> dtoList = assessments.stream()
                .map(CoreCompetencyAssessmentDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * 사용자가 응시할 수 있는 평가 목록 조회 API
     * (응시 완료 여부 포함)
     */
    @GetMapping
    public ResponseEntity<List<AssessmentListResponseDto>> getAssessmentList() {
        // 1. Spring Security의 SecurityContextHolder 등에서 현재 로그인한 유저 정보를 가져옵니다.
        //    (예시: Principal 객체나 @AuthenticationPrincipal 어노테이션 사용)
        String currentStudentNo = "2025108001"; // <<-- 실제로는 SecurityContext에서 가져와야 함

        // 2. 서비스 메소드에 학생 정보를 전달합니다.
        List<AssessmentListResponseDto> assessments = diagnosisService.findAssessmentsForStudent(currentStudentNo);

        return ResponseEntity.ok(assessments);
    }
}