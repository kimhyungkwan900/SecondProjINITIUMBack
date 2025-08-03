package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.ResponseChoiceOptionRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CoreCompetencyResponseServiceTest {

    @InjectMocks
    private CoreCompetencyResponseService coreCompetencyResponseService;

    @Mock
    private CoreCompetencyAssessmentRepository assessmentRepository;

    @Mock
    private CoreCompetencyQuestionRepository questionRepository;

    @Mock
    private ResponseChoiceOptionRepository optionRepository;

    @Mock
    private CoreCompetencyResponseRepository responseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("학생 응답 저장 서비스 테스트")
    void saveStudentResponseTest() {
        // Given
        Long studentId = 1L;
        Long assessmentId = 10L;
        Long questionId = 100L;
        Long optionId = 1000L;

        Student student = mock(Student.class); // 단순 Mock 객체

        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder().id(assessmentId).assessmentName("평가1").build();
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).description("문항1").build();
        ResponseChoiceOption option = ResponseChoiceOption.builder().id(optionId).score(5).label("보기1").build();

        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));

        // When
        coreCompetencyResponseService.saveStudentResponse(student, assessmentId, questionId, optionId);

        // Then
        verify(responseRepository, times(1)).save(any(CoreCompetencyResponse.class));
    }
}
