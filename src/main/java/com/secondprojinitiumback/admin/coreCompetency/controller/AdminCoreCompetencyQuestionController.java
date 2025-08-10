package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateRequestDto;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCompetencyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/core-competency/question")
@RequiredArgsConstructor
public class AdminCoreCompetencyQuestionController {

    private final AdminCoreCompetencyQuestionService adminCoreCompetencyQuestionService;

    // 1. 문항 등록
    @PostMapping("/create/{assessmentId}")
    public ResponseEntity<CoreCompetencyQuestion> createCoreCompetencyQuestion(
            @PathVariable Long assessmentId,
            @RequestBody CoreCompetencyQuestionCreateRequestDto dto
    ) {
        return ResponseEntity.ok(
                adminCoreCompetencyQuestionService.createCoreCompetencyQuestion(assessmentId, dto)
        );
    }

    // 2. 문항 수정(기본정보 + 옵션 라벨/점수만; 개수는 여기서 변경 불가)
    @PutMapping("/update/{questionId}")
    public ResponseEntity<CoreCompetencyQuestion> updateCoreCompetencyQuestion(
            @PathVariable Long questionId,
            @RequestBody CoreCompetencyQuestionCreateRequestDto dto
    ) {
        return ResponseEntity.ok(
                adminCoreCompetencyQuestionService.updateCoreCompetencyQuestion(questionId, dto)
        );
    }

    // 3. 문항 삭제(동일 묶음 번호/정렬 재정렬)
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<Void> deleteCoreCompetencyQuestion(@PathVariable Long questionId) {
        adminCoreCompetencyQuestionService.deleteCoreCompetencyQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    // 4. 문항 단건 조회
    @GetMapping("/get/{questionId}")
    public ResponseEntity<CoreCompetencyQuestion> getCoreCompetencyQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(
                adminCoreCompetencyQuestionService.getCoreCompetencyQuestion(questionId)
        );
    }

    // 5. 문항 전체 조회
    @GetMapping("/get/all")
    public ResponseEntity<List<CoreCompetencyQuestion>> getAllCoreCompetencyQuestions() {
        return ResponseEntity.ok(
                adminCoreCompetencyQuestionService.getAllCoreCompetencyQuestions()
        );
    }

    // 6. 드롭다운 변경: 해당 문항 1개의 옵션 개수 재생성(개수 변경 전용)
    // 예: PATCH /api/admin/core-competency/question/123/option-count?count=5
    @PatchMapping("/{questionId}/option-count")
    public ResponseEntity<CoreCompetencyQuestion> setAnswerOptionCount(
            @PathVariable Long questionId,
            @RequestParam("count") int count
    ) {
        return ResponseEntity.ok(
                adminCoreCompetencyQuestionService.setAnswerOptionCount(questionId, count)
        );
    }
}
