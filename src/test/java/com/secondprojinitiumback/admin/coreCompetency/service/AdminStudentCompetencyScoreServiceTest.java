package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResponseRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyResultRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.StudentCompetencyScoreRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * StudentCompetencyScoreServiceTest
 * - 핵심역량 진단 결과(CoreCompetencyResult)를 기반으로 하위역량별 점수를 계산하고
 *   StudentCompetencyScore 엔티티로 저장하는 기능을 테스트함
 */
@ExtendWith(MockitoExtension.class)
class AdminStudentCompetencyScoreServiceTest {

    @InjectMocks
    private AdminStudentCompetencyScoreService adminStudentCompetencyScoreService;

    @Mock
    private CoreCompetencyResponseRepository responseRepository;

    @Mock
    private StudentCompetencyScoreRepository scoreRepository;

    @Mock
    private CoreCompetencyResultRepository resultRepository;

    /**
     * [하위역량 점수 저장 테스트]
     * - 학생의 응답 결과를 바탕으로 하위역량별 점수를 계산하여 저장
     * - 저장 전 기존 점수를 삭제하고, 새 점수를 저장하는 흐름을 검증
     */
    @Test
    @DisplayName("학생의 역량 점수 저장 테스트")
    void saveStudentCompetencyScore() {
        // Given 

        // 테스트용 학생 객체
        Student student = Student.builder()
                .studentNo("2023001")
                .build();

        // 평가 정보
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder()
                .id(1L)
                .assessmentName("역량 평가")
                .build();

        // 하위역량, 행동지표, 문항 구성
        SubCompetencyCategory subCompetencyCategory = SubCompetencyCategory.builder()
                .id(1L)
                .subCategoryName("의사소통 역량")
                .build();

        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .id(1L)
                .description("문항1")
                .subCompetencyCategory(subCompetencyCategory)
                .build();

        // 응답한 선택지 (점수 포함)
        ResponseChoiceOption selectedOption = ResponseChoiceOption.builder()
                .id(1L)
                .label("매우 그렇다")
                .score(4)
                .build();

        // 학생의 응답
        CoreCompetencyResponse response = CoreCompetencyResponse.builder()
                .assessment(assessment)
                .student(student)
                .question(question)
                .selectedOption(selectedOption)
                .resultScore(4)
                .build();

        // 평가 결과 객체
        CoreCompetencyResult result = CoreCompetencyResult.builder()
                .assessment(assessment)
                .student(student)
                .response(response)
                .build();

        // Mock 설정
        // 학생이 해당 평가에서 한 응답 목록
        when(responseRepository.findByStudentAndAssessment(student, assessment))
                .thenReturn(List.of(response));

        // 해당 평가에 참여한 학생들의 결과 (자기 자신 1명만 포함)
        when(resultRepository.findByAssessment(assessment))
                .thenReturn(List.of(result));

        // 이전에 저장된 점수 (전체 평균 계산용)
        when(scoreRepository.findByResult(result))
                .thenReturn(List.of(
                        StudentCompetencyScore.builder()
                                .result(result)
                                .subCategory(subCompetencyCategory)
                                .standardScore(BigDecimal.valueOf(4.0))
                                .build()
                ));

        // When
        adminStudentCompetencyScoreService.saveStudentScoreBySubCompetency(result);

        // Then
        // 기존 점수 삭제 확인
        verify(scoreRepository).deleteByResult(result);

        // 저장된 점수를 캡처하여 확인
        ArgumentCaptor<List<StudentCompetencyScore>> captor = ArgumentCaptor.forClass(List.class);
        verify(scoreRepository).saveAll(captor.capture());

        List<StudentCompetencyScore> savedScores = captor.getValue();
        assertEquals(1, savedScores.size(), "저장된 점수는 1개여야 함");

        StudentCompetencyScore score = savedScores.getFirst();

        // 저장된 점수 내용 검증
        assertEquals(subCompetencyCategory, score.getSubCategory(), "하위역량 매핑 확인");
        assertEquals(BigDecimal.valueOf(4.00).setScale(2), score.getStandardScore(), "개인 점수 계산 확인");
        assertEquals(BigDecimal.valueOf(4.00).setScale(2), score.getTotalAverageScore(), "전체 평균 점수 계산 확인");
        assertEquals("보통", score.getClassificationCode(), "분류코드 확인 (4.0 → 보통)");
    }
}

