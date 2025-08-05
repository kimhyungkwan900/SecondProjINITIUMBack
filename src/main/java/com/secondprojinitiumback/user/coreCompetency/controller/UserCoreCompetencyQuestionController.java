//package com.secondprojinitiumback.user.coreCompetency.controller;
//
//import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionDto;
//import com.secondprojinitiumback.admin.coreCompetency.service.CoreCompetencyResponseService;
//import com.secondprojinitiumback.admin.coreCompetency.service.CoreCompetencyResultService;
//import com.secondprojinitiumback.user.coreCompetency.service.UserCoreCompetencyQuestionService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/user/assessment")
//public class UserCoreCompetencyQuestionController {
//
//    private final UserCoreCompetencyQuestionService questionService;
//    private final CoreCompetencyResponseService responseService;
//    private final CoreCompetencyResultService resultService;
//
//    //문항 + 선택지 조회(페이징 처리)
//    @GetMapping("/assessmentId/questions")
//    public ResponseEntity<Page<CoreCompetencyQuestionDto>> getAllQuestions(@PathVariable Long assessmentId, @RequestParam int page, @RequestParam(defaultValue = "20") int size) {
//        return ResponseEntity.ok(questionService.getQuestionsWithOptions(assessmentId, page, size));
//    }
//
//    //응답 저장
//    @PostMapping("/assessmentId/questions//responses")
//    public ResponseEntity<Void> submitASsessmentResponses(@PathVariable Long assessmentId,@RequestParam Long studentId, @RequestBody List<CoreCompetencyQuestionDto> responses) {
//        responseService.saveStudentResponse(assessmentId, studentId, responses);
//        return ResponseEntity.ok().build();
//    }
//
//    //진단 완료 처리 및 결과 계산
//
//
//}
