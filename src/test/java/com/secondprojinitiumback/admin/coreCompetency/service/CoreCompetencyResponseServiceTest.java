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

/**
 * CoreCompetencyResponseService의 단위 테스트 클래스
 * - 학생이 핵심역량 평가에서 응답을 제출했을 때, 응답이 DB에 정상 저장되는지 검증
 */
class CoreCompetencyResponseServiceTest {

    // 테스트 대상 서비스
    @InjectMocks
    private CoreCompetencyResponseService coreCompetencyResponseService;

    // 의존성으로 주입될 Repository들 모킹
    @Mock
    private CoreCompetencyAssessmentRepository assessmentRepository;

    @Mock
    private CoreCompetencyQuestionRepository questionRepository;

    @Mock
    private ResponseChoiceOptionRepository optionRepository;

    @Mock
    private CoreCompetencyResponseRepository responseRepository;

    /**
     * 테스트 실행 전 Mock 객체 초기화
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * [학생 응답 저장 테스트]
     * - 평가 ID, 문항 ID, 선택지 ID, 학생 객체가 주어졌을 때,
     *   CoreCompetencyResponse 엔티티가 정상적으로 생성되어 저장되는지 검증
     */
    @Test
    @DisplayName("학생 응답 저장 서비스 테스트")
    void saveStudentResponseTest() {
        // Given
        Long assessmentId = 10L;
        Long questionId = 100L;
        Long optionId = 1000L;

        Student student = mock(Student.class); // 학생 객체는 단순 mock 처리

        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .id(assessmentId)
                .assessmentName("평가1")
                .build();

        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .id(questionId)
                .description("문항1")
                .build();

        ResponseChoiceOption option = ResponseChoiceOption.builder()
                .id(optionId)
                .label("보기1")
                .score(5)
                .build();

        // 각 ID로 엔티티 조회 시 결과를 반환하도록 설정
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));

        // When: 응답 저장 서비스 호출
        coreCompetencyResponseService.saveStudentResponse(student, assessmentId, questionId, optionId);

        // Then: 응답 저장 로직이 정확히 1번 실행되었는지 검증
        verify(responseRepository, times(1)).save(any(CoreCompetencyResponse.class));
    }
}

