package com.secondprojinitiumback.user.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.repository.*;
import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencySubmitDto;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserCoreCompetencySubmitService {

    private final CoreCompetencyAssessmentRepository coreCompetencyAssessmentRepository;
    private final StudentRepository studentRepository;
    private final CoreCompetencyQuestionRepository coreCompetencyQuestionRepository;
    private final ResponseChoiceOptionRepository responseChoiceOptionRepository;
    private final CoreCompetencyResponseRepository coreCompetencyResponseRepository;

    /**
     * 핵심역량 진단 응답 저장 서비스
     * - 평가 ID와 학번으로 해당 학생의 평가 응답을 저장함
     * - 각 응답 항목(questionId, label, score)에 따라 Response 생성
     */
    @Transactional
    public void submitResponses(UserCoreCompetencySubmitDto dto, String studentNo) {
        // 1. 평가 및 학생 정보 조회
        CoreCompetencyAssessment assessment = coreCompetencyAssessmentRepository.findById(dto.getAssessmentId())
                .orElseThrow(() -> new IllegalArgumentException("해당 평가가 존재하지 않습니다."));

        Student student = studentRepository.findByStudentNo(studentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

        // 2. 응답 항목 순회 및 저장
        for (UserCoreCompetencySubmitDto.ResponseItem responseDto : dto.getResponse()) {
            // 문항 조회
            CoreCompetencyQuestion question = coreCompetencyQuestionRepository.findById(responseDto.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 문항이 존재하지 않습니다."));

            // 선택지 조회 (문항 ID + 라벨 기준)
            ResponseChoiceOption responseChoiceOption = responseChoiceOptionRepository
                    .findByQuestionIdAndLabel(responseDto.getQuestionId(), responseDto.getLabel())
                    .orElseThrow(() -> new IllegalArgumentException("해당 선택지가 존재하지 않습니다."));

            // 응답 객체 생성
            CoreCompetencyResponse response = CoreCompetencyResponse.builder()
                    .assessment(assessment)
                    .student(student)
                    .question(question)
                    .selectedOption(responseChoiceOption)
                    .choiceLabel(responseDto.getLabel())
                    .selectCount(responseDto.getScore())
                    .completeDate(LocalDate.now().toString())
                    .build();

            // DB에 저장
            coreCompetencyResponseRepository.save(response);
        }
    }
}
