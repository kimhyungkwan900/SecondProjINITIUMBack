package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import com.secondprojinitiumback.admin.coreCompetency.service.StudentCompetencyScoreService;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/core-competency/score")
@RequiredArgsConstructor
public class StudentScoreController {

    private final StudentCompetencyScoreService scoreService;
    private final CoreCompetencyResultRepository resultRepository;

    /**
     * 특정 결과 ID를 바탕으로 하위역량별 학생 점수 저장 (관리자 호출용)
     */
    @PostMapping("/{resultId}/save")
    public String saveStudentScore(@PathVariable Long resultId) {
        CoreCompetencyResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("진단 결과를 찾을 수 없습니다."));

        scoreService.saveStudentScoreBySubCompetency(result);
        return "학생 점수 저장 완료";
    }
}
