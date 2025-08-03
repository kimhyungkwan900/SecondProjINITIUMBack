package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.ResponseChoiceOptionRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 학생의 핵심역량 응답(CoreCompetencyResponse)을 저장하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class CoreCompetencyResponseService {

    // 평가, 문항, 선택지, 응답 저장을 위한 레포지토리들
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final ResponseChoiceOptionRepository responseChoiceOptionRepository;
    private final CoreCompetencyResponseRepository coreCompetencyResponseRepository;

    /**
     * 학생이 문항에 응답한 결과를 저장함
     * @param student 응답한 학생
     * @param assessmentId 해당 평가 ID
     * @param questionId 응답한 문항 ID
     * @param optionId 선택한 보기 ID
     */
    @Transactional
    public void saveStudentResponse(Student student, Long assessmentId, Long questionId, Long optionId) {
        // 문항 정보 조회 (없으면 예외)
        CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("문항 없음"));

        // 선택한 보기 정보 조회 (없으면 예외)
        ResponseChoiceOption selectedOption = responseChoiceOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("선택지 없음"));

        // 선택된 보기의 점수를 추출하여 결과 점수로 사용
        // 이 점수는 해당 문항에 대해 학생이 선택한 응답의 점수를 의미하며,
        // 전체 평가 점수는 이후 모든 문항에 대한 resultScore를 합산하여 계산함
        int score = selectedOption.getScore();

        // 핵심역량 응답 엔티티 생성
        CoreCompetencyResponse coreCompetencyResponse = CoreCompetencyResponse.builder()
                .student(student)                         // 응답한 학생
                .question(question)                       // 문항 정보
                .selectedOption(selectedOption)           // 선택한 보기
                .completeDate(LocalDate.now().toString()) // 응답 완료 날짜 (yyyy-MM-dd 형식)
                .resultScore(score)                       // 점수 (해당 문항의 보기 기준 점수)
                .selectCount(1)                           // 선택 횟수 기본값 1
                .build();

        // DB에 응답 저장
        coreCompetencyResponseRepository.save(coreCompetencyResponse);
    }
}
