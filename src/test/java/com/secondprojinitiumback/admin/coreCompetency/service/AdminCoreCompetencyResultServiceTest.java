package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyAssessmentRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * CoreCompetencyResultServiceTest
 * - 학생이 응답한 핵심역량 평가 결과를 조회하는 기능을 단위 테스트함
 * - 핵심 기능: 특정 평가에 대해 학생이 각 문항에 대해 선택한 보기(Label)를 문항 번호로 매핑하여 반환
 */
@ExtendWith(MockitoExtension.class)
public class AdminCoreCompetencyResultServiceTest {

    // 테스트 대상 서비스
    @InjectMocks
    private AdminCoreCompetencyResultService adminCoreCompetencyResultService;

    // 의존성 Mock 객체
    @Mock
    private CoreCompetencyResponseRepository coreCompetencyResponseRepository;

    @Mock
    private CoreCompetencyAssessmentRepository assessmentRepository;

    /**
     * [학생 응답 보기 라벨 조회 테스트]
     * - 특정 학생이 특정 평가에 응답한 결과를 기반으로
     *   문항 번호 → 선택지 라벨 매핑 결과가 정확히 반환되는지 검증
     */
    @Test
    @DisplayName("학생의 역량 점수 저장 테스트")
    public void getStudentResponseLabels() {
        // Given: 학생, 평가, 문항, 선택지 및 응답 객체 생성
        Student student = Student.builder()
                .studentNo("2023001")
                .build();

        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .id(1L)
                .assessmentName("역량 평가")
                .build();

        CoreCompetencyQuestion question1 = CoreCompetencyQuestion.builder()
                .questionNo(1)
                .build();

        CoreCompetencyQuestion question2 = CoreCompetencyQuestion.builder()
                .questionNo(2)
                .build();

        ResponseChoiceOption option1 = ResponseChoiceOption.builder()
                .id(1L)
                .label("매우그렇다")
                .score(5)
                .build();

        ResponseChoiceOption option2 = ResponseChoiceOption.builder()
                .id(2L)
                .label("그렇다")
                .score(4)
                .build();

        CoreCompetencyResponse response1 = CoreCompetencyResponse.builder()
                .question(question1)
                .selectedOption(option1)
                .build();

        CoreCompetencyResponse response2 = CoreCompetencyResponse.builder()
                .question(question2)
                .selectedOption(option2)
                .build();

        // Mock 설정: 평가 및 응답 결과 반환
        when(assessmentRepository.findById(assessment.getId()))
                .thenReturn(Optional.of(assessment));

        when(coreCompetencyResponseRepository.findByStudentAndAssessment(student, assessment))
                .thenReturn(List.of(response1, response2));

        // When: 서비스 호출
        Map<Integer, String> result = adminCoreCompetencyResultService.getAssessmentResults(assessment.getId());

        // Then: 문항 번호 → 선택지 라벨 매핑 결과 검증
        assertEquals("매우그렇다", result.get(1));
        assertEquals("그렇다", result.get(2));

        // 호출 여부 검증
        verify(assessmentRepository).findById(assessment.getId());
        verify(coreCompetencyResponseRepository).findByStudentAndAssessment(student, assessment);
    }
}
