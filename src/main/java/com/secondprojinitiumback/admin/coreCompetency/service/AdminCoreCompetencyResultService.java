// CoreCompetencyResultService.java
package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.QuestionStatisticsDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminCoreCompetencyResultService {

    private final CoreCompetencyAssessmentRepository assessmentRepository;
    private final CoreCompetencyResponseRepository responseRepository;

    /**
     * [학생 응답 결과] 학생이 평가에서 각 문항에 대해 어떤 선택지를 선택했는지 보여줌
     */
    public Map<Integer, String> getStudentResponseLabels(Student student, Long assessmentId) {
        CoreCompetencyAssessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new IllegalArgumentException("평가를 찾을 수 없습니다."));

        List<CoreCompetencyResponse> responses = responseRepository.findByStudentAndAssessment(student, assessment);

        return responses.stream()
                .collect(Collectors.toMap(
                        r -> r.getQuestion().getQuestionNo(),           // 문항 번호
                        r -> r.getSelectedOption().getLabel()          // 선택한 보기 라벨
                ));
    }

    /**
     * [총계표 데이터 생성] 문항별로 응답자 수, 선택 분포(1~5), 평균 점수를 계산하여 반환
     */
    public List<QuestionStatisticsDto> getTotalQuestionStatistics(Long assessmentId) {
        List<CoreCompetencyResponse> responses = responseRepository.findByAssessmentId(assessmentId);

        // 문항별로 응답 리스트 그룹핑
        Map<CoreCompetencyQuestion, List<CoreCompetencyResponse>> grouped = responses.stream()
                .collect(Collectors.groupingBy(CoreCompetencyResponse::getQuestion));

        List<QuestionStatisticsDto> result = new ArrayList<>();

        for (Map.Entry<CoreCompetencyQuestion, List<CoreCompetencyResponse>> entry : grouped.entrySet()) {
            CoreCompetencyQuestion question = entry.getKey();
            List<CoreCompetencyResponse> resps = entry.getValue();

            // 1~5점 선택 분포 카운트
            int[] counts = new int[5];
            for (CoreCompetencyResponse r : resps) {
                int score = r.getResultScore();
                if (score >= 1 && score <= 5) counts[score - 1]++;
            }

            // 평균 점수 계산
            double avg = resps.stream().mapToInt(CoreCompetencyResponse::getResultScore).average().orElse(0.0);

            // 총계표 DTO 생성
            result.add(QuestionStatisticsDto.builder()
                    .questionNo(question.getQuestionNo())
                    .questionName(question.getName())
                    .subCategoryName(question.getSubCompetencyCategory().getSubCategoryName())
                    .responseCount(resps.size())
                    .choiceCounts(counts)
                    .averageScore(BigDecimal.valueOf(avg).setScale(2, RoundingMode.HALF_UP))
                    .build());
        }

        return result;
    }
}
