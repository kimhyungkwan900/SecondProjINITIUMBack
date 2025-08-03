// StudentCompetencyScoreService.java
package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.repository.*;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentCompetencyScoreService {

    private final CoreCompetencyResponseRepository coreCompetencyResponseRepository;
    private final StudentCompetencyScoreRepository studentCompetencyScoreRepository;
    private final CoreCompetencyResultRepository coreCompetencyResultRepository;

    /**
     * 학생의 평가 결과(CoreCompetencyResult)를 기반으로
     * 하위역량별 점수를 계산하고 분류코드를 생성하여 DB에 저장한다.
     */
    public void saveStudentScoreBySubCompetency(CoreCompetencyResult result) {
        Student student = result.getStudent();
        CoreCompetencyAssessment assessment = result.getAssessment();

        // 1. 해당 학생의 응답 전체 조회
        List<CoreCompetencyResponse> responses = coreCompetencyResponseRepository.findByStudentAndAssessment(student, assessment);

        // 2. 하위역량별로 점수들을 그룹핑할 Map 생성
        Map<SubCompetencyCategory, List<Integer>> scoreMap = new HashMap<>();

        for (CoreCompetencyResponse response : responses) {
            CoreCompetencyQuestion question = response.getQuestion();
            BehaviorIndicator indicator = question.getBehaviorIndicator();
            SubCompetencyCategory subCategory = indicator.getSubCompetencyCategory();

            if (subCategory == null) continue;

            // 하위역량 기준으로 점수를 그룹핑
            scoreMap.computeIfAbsent(subCategory, k -> new ArrayList<>())
                    .add(response.getResultScore());
        }

        // 3. 기존 점수 삭제 (재계산 대비)
        studentCompetencyScoreRepository.deleteByResult(result);

        List<StudentCompetencyScore> savedScores = new ArrayList<>();

        // 4. 하위역량별 평균 점수 계산 및 개인 점수 객체 생성
        for (Map.Entry<SubCompetencyCategory, List<Integer>> entry : scoreMap.entrySet()) {
            SubCompetencyCategory sub = entry.getKey();
            List<Integer> scores = entry.getValue();

            double avg = scores.stream().mapToInt(i -> i).average().orElse(0.0);
            BigDecimal scoreDecimal = BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP);

            // 개인 점수 및 분류코드 포함하여 객체 생성
            StudentCompetencyScore score = StudentCompetencyScore.builder()
                    .result(result)
                    .subCategory(sub)
                    .standardScore(scoreDecimal)              // 개인 점수
                    .totalAverageScore(scoreDecimal)          // 전체 평균은 아래에서 다시 계산됨
                    .classificationCode(classify(avg))        // 분류코드: 우수/보통/미흡
                    .build();

            savedScores.add(score);
        }

        // 5. 같은 평가를 받은 모든 학생들의 결과 조회
        List<CoreCompetencyResult> allResults = coreCompetencyResultRepository.findByAssessment(assessment);

        // 6. 하위역량별 전체 점수 리스트 생성 (전체 평균 계산용)
        Map<SubCompetencyCategory, List<BigDecimal>> totalScoreMap = new HashMap<>();

        for (CoreCompetencyResult res : allResults) {
            List<StudentCompetencyScore> scores = studentCompetencyScoreRepository.findByResult(res);

            for (StudentCompetencyScore score : scores) {
                if (score.getSubCategory() == null || score.getStandardScore() == null) continue;

                // 하위역량 기준으로 전체 점수 수집
                totalScoreMap.computeIfAbsent(score.getSubCategory(), k -> new ArrayList<>())
                        .add(score.getStandardScore());
            }
        }

        // 7. 전체 평균 점수 계산 후 각 객체에 반영
        for (StudentCompetencyScore score : savedScores) {
            SubCompetencyCategory sub = score.getSubCategory();
            List<BigDecimal> list = totalScoreMap.get(sub);

            if (list != null && !list.isEmpty()) {
                double avg = list.stream().mapToDouble(BigDecimal::doubleValue).average().orElse(0.0);
                score.setTotalAverageScore(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP));
            } else {
                score.setTotalAverageScore(score.getStandardScore()); // 전체 평균이 없으면 개인 점수로 대체
            }
        }

        // 8. 최종 점수 리스트를 DB에 저장
        studentCompetencyScoreRepository.saveAll(savedScores);
    }

    /**
     * 평균 점수 기반으로 분류코드 반환
     * 4.5 이상: 우수 / 3.0 이상: 보통 / 그 외: 미흡
     */
    private String classify(double avg) {
        if (avg >= 4.5) return "우수";
        else if (avg >= 3.0) return "보통";
        else return "미흡";
    }
}
