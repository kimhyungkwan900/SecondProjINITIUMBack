package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import com.secondprojinitiumback.admin.coreCompetency.service.AdminStudentCompetencyScoreService;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResultRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/core-competency/score")
@RequiredArgsConstructor
public class StudentScoreController {

    private final AdminStudentCompetencyScoreService scoreService;
    private final CoreCompetencyResultRepository resultRepository;

    /**
     * 특정 결과 ID를 바탕으로 하위역량별 학생 점수 저장 (관리자 호출용)
     */
    @PostMapping("/{resultId}/save")
    public String saveStudentScore(@PathVariable Long resultId) {
        CoreCompetencyResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_RESULT_NOT_FOUND));

        scoreService.saveStudentScoreBySubCompetency(result);
        return "학생 점수 저장 완료";
    }
}
