package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateDto;
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

    //1. 문항 등록
    @PostMapping("/create/{assessmentId}")
    public ResponseEntity<CoreCompetencyQuestion> createCoreCompetencyQuestion(@PathVariable Long assessmentId, @RequestBody CoreCompetencyQuestionCreateDto coreCompetencyQuestionCreateDto) {
        return ResponseEntity.ok(adminCoreCompetencyQuestionService.createCoreCompetencyQuestion(assessmentId, coreCompetencyQuestionCreateDto));
    }

    //2. 문항 수정
    @PutMapping("/update/{questionId}")
    public ResponseEntity<CoreCompetencyQuestion> updateCoreCompetencyQuestion(@PathVariable Long questionId, @RequestBody CoreCompetencyQuestionCreateDto coreCompetencyQuestionCreateDto) {
        return ResponseEntity.ok(adminCoreCompetencyQuestionService.updateCoreCompetencyQuestion(questionId, coreCompetencyQuestionCreateDto));
    }

    //3. 문항 삭제
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<Void> deleteCoreCompetencyQuestion(@PathVariable Long questionId) {
        adminCoreCompetencyQuestionService.deleteCoreCompetencyQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    //4. 문항 상세 조회
    @GetMapping("/get/{questionId}")
    public ResponseEntity<CoreCompetencyQuestion> getCoreCompetencyQuestion(@PathVariable Long questionId) {
        return ResponseEntity.ok(adminCoreCompetencyQuestionService.getCoreCompetencyQuestion(questionId));
    }

    //5. 문항 전체 조회
    @GetMapping("/get/all")
    public ResponseEntity<List<CoreCompetencyQuestion>> getAllCoreCompetencyQuestions() {
        return ResponseEntity.ok(adminCoreCompetencyQuestionService.getAllCoreCompetencyQuestions());
    }
}
