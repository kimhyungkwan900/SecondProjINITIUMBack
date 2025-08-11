package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateRequestDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionResponseDto;
import com.secondprojinitiumback.admin.coreCompetency.dto.SubCompetencyCategoryDto;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCompetencyQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coreCompetency/question")
@RequiredArgsConstructor
public class AdminCoreCompetencyQuestionController {

    private final AdminCoreCompetencyQuestionService adminCoreCompetencyQuestionService;

    // 1. 문항 등록
    @PostMapping("/create/{assessmentId}")
    public ResponseEntity<CoreCompetencyQuestionResponseDto> createCoreCompetencyQuestion(
            @PathVariable Long assessmentId,
            @RequestBody CoreCompetencyQuestionCreateRequestDto dto
    ) {
        CoreCompetencyQuestion savedQuestion = adminCoreCompetencyQuestionService.createCoreCompetencyQuestion(assessmentId, dto);
        return ResponseEntity.ok(CoreCompetencyQuestionResponseDto.fromEntity(savedQuestion));
    }

    // 2. 문항 수정
    @PutMapping("/update/{questionId}")
    public ResponseEntity<CoreCompetencyQuestionResponseDto> updateCoreCompetencyQuestion(
            @PathVariable Long questionId,
            @RequestBody CoreCompetencyQuestionCreateRequestDto dto
    ) {
        CoreCompetencyQuestion updatedQuestion = adminCoreCompetencyQuestionService.updateCoreCompetencyQuestion(questionId, dto);
        return ResponseEntity.ok(CoreCompetencyQuestionResponseDto.fromEntity(updatedQuestion));
    }

    // 3. 문항 삭제 (수정할 필요 없음 - 완벽합니다)
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<Void> deleteCoreCompetencyQuestion(@PathVariable Long questionId) {
        adminCoreCompetencyQuestionService.deleteCoreCompetencyQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    // 4. 문항 단건 조회 (수정할 필요 없음 - 완벽합니다)
    @GetMapping("/get/{questionId}")
    public ResponseEntity<CoreCompetencyQuestionResponseDto> getCoreCompetencyQuestion(@PathVariable Long questionId) {
        CoreCompetencyQuestion questionEntity = adminCoreCompetencyQuestionService.getCoreCompetencyQuestion(questionId);
        return ResponseEntity.ok(CoreCompetencyQuestionResponseDto.fromEntity(questionEntity));
    }

    // 5. 문항 전체 조회 (수정할 필요 없음 - 완벽합니다)
    @GetMapping("/get/all")
    public ResponseEntity<List<CoreCompetencyQuestionResponseDto>> getAllCoreCompetencyQuestions() {
        List<CoreCompetencyQuestion> questionEntities = adminCoreCompetencyQuestionService.getAllCoreCompetencyQuestions();
        List<CoreCompetencyQuestionResponseDto> responseDtos = questionEntities.stream()
                .map(CoreCompetencyQuestionResponseDto::fromEntity)
                .toList();
        return ResponseEntity.ok(responseDtos);
    }

    // 6. 선택지 개수 변경
    @PatchMapping("/{questionId}/option-count")
    public ResponseEntity<CoreCompetencyQuestionResponseDto> setAnswerOptionCount(
            @PathVariable Long questionId,
            @RequestParam("count") int count
    ) {
        CoreCompetencyQuestion updatedQuestion = adminCoreCompetencyQuestionService.setOptionCount(questionId, count);
        return ResponseEntity.ok(CoreCompetencyQuestionResponseDto.fromEntity(updatedQuestion));
    }


     // 7. 특정 평가(assessment)에 속한 모든 문항 목록을 조회하는 API
    @GetMapping("/assessment/{assessmentId}")
    public ResponseEntity<List<CoreCompetencyQuestionResponseDto>> getQuestionsByAssessmentId(
            @PathVariable Long assessmentId
    ) {
        List<CoreCompetencyQuestion> questions = adminCoreCompetencyQuestionService.getQuestionsByAssessmentId(assessmentId);
        List<CoreCompetencyQuestionResponseDto> dtos = questions.stream()
                .map(CoreCompetencyQuestionResponseDto::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    //8. 특정 평가(assessment)에 속한 모든 하위 역량 목록을 조회하는 API
    @GetMapping("/assessment/{assessmentId}/subcategories")
    public ResponseEntity<List<SubCompetencyCategoryDto>> getSubCategoriesByAssessmentId(
            @PathVariable Long assessmentId
    ) {
        List<SubCompetencyCategory> subs = adminCoreCompetencyQuestionService.getSubCategoriesByAssessmentId(assessmentId);
        List<SubCompetencyCategoryDto> dtos = subs.stream()
                .map(SubCompetencyCategoryDto::fromEntity) // id, name, (code/desc 필요 없으면 fromEntity를 더 슬림하게 써도 OK)
                .map(dto -> SubCompetencyCategoryDto.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .build()
                ).toList();
        return ResponseEntity.ok(dtos);
    }


}