package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.dto.QuestionStatisticsDto;
import com.secondprojinitiumback.admin.coreCompetency.service.CoreCompetencyResultService;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/core-competency/result")
@RequiredArgsConstructor
public class AdminResultStatisticsController {

    private final CoreCompetencyResultService resultService;
    private final StudentRepository studentRepository;

    /**
     * 학생의 평가 응답 보기(문항 번호 → 선택지 라벨) 조회
     */
    @GetMapping("/student/{studentNo}/assessment/{assessmentId}/labels")
    public Map<Integer, String> getStudentLabels(
            @PathVariable Student studentNo,
            @PathVariable Long assessmentId
    ) {
        return resultService.getStudentResponseLabels(studentNo, assessmentId);
    }

    /**
     * 전체 평가 문항에 대한 응답 통계 (총계표)
     */
    @GetMapping("/assessment/{assessmentId}/statistics")
    public List<QuestionStatisticsDto> getTotalStatistics(@PathVariable Long assessmentId) {
        return resultService.getTotalQuestionStatistics(assessmentId);
    }
}
