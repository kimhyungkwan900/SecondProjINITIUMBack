package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.ResponseChoiceOptionRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.UserResponseBulkRequestDto;
import com.secondprojinitiumback.user.coreCompetency.dto.UserResponseRequestDto;
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
public class AdminCoreCompetencyResponseService {

    // 평가, 문항, 선택지, 응답 저장을 위한 레포지토리들
    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final ResponseChoiceOptionRepository responseChoiceOptionRepository;
    private final CoreCompetencyResponseRepository coreCompetencyResponseRepository;

    /**
     * 학생이 문항에 응답한 결과를 저장함
     */
    @Transactional
    public void saveResponsesByLabel(Student student, UserResponseBulkRequestDto dto) {
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(dto.getAssessmentId())
                .orElseThrow(() -> new IllegalArgumentException("평가 없음"));

        for (UserResponseRequestDto responseDto : dto.getResponses()) {
            CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(responseDto.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("문항 없음"));

            ResponseChoiceOption selectedOption = responseChoiceOptionRepository
                    .findByQuestionIdAndLabel(question.getId(), responseDto.getLabel())
                    .orElseThrow(() -> new IllegalArgumentException("해당 보기 없음"));

            CoreCompetencyResponse response = CoreCompetencyResponse.builder()
                    .student(student)
                    .question(question)
                    .assessment(assessment)
                    .selectedOption(selectedOption)
                    .resultScore(selectedOption.getScore())
                    .completeDate(LocalDate.now().toString())
                    .selectCount(1)
                    .build();

            coreCompetencyResponseRepository.save(response);
        }
    }
}
