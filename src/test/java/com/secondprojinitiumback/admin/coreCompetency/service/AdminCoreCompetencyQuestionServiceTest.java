package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateRequestDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.*;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminCoreCompetencyQuestionServiceTest {

    @InjectMocks
    private AdminCoreCompetencyQuestionService service;

    @Mock private CoreCompetencyQuestionRepository questionRepository;
    @Mock private CoreCompetencyAssessmentRepository assessmentRepository;

    // [1] 공통 문항 생성 테스트
    @Test
    @DisplayName("공통 문항 생성 테스트")
    void createCommonQuestion_success() {
        // Given
        Long assessmentId = 1L;
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder().id(assessmentId).build();
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));

        CoreCompetencyQuestionCreateRequestDto dto = CoreCompetencyQuestionCreateRequestDto.builder()
                .questionNo(1)
                .questionName("질문")
                .questionContent("내용")
                .displayOrder(1)
                .selectAllowCount(1)
                .build();

        when(questionRepository.save(any(CoreCompetencyQuestion.class)))
                .thenReturn(CoreCompetencyQuestion.builder().id(1L).build());

        // When
        CoreCompetencyQuestion result = service.createCoreCompetencyQuestion(assessmentId, dto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(questionRepository, times(1)).save(any(CoreCompetencyQuestion.class));
    }

    // [2] 문항 삭제 테스트 (단순 삭제)
    @Test
    @DisplayName("문항 삭제 테스트")
    void deleteQuestion_success() {
        // Given
        Long questionId = 11L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // When
        service.deleteCoreCompetencyQuestion(questionId);

        // Then
        verify(questionRepository).delete(question);
    }

    // [3] 존재하지 않는 문항 조회 시 예외
    @Test
    @DisplayName("존재하지 않는 문항 조회 시 예외")
    void getQuestion_notFound() {
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.getCoreCompetencyQuestion(99L));
    }

    // [4] 문항 상세 조회 테스트
    @Test
    @DisplayName("문항 상세 조회 테스트")
    void getQuestion() {
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        CoreCompetencyQuestion result = service.getCoreCompetencyQuestion(questionId);

        assertNotNull(result);
        assertEquals(questionId, result.getId());
        verify(questionRepository).findById(questionId);
    }

    // [5] 전체 문항 리스트 조회 테스트
    @Test
    @DisplayName("전체 문항 조회 테스트")
    void getAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        List<CoreCompetencyQuestion> result = service.getAllCoreCompetencyQuestions();

        assertTrue(result.isEmpty());
        verify(questionRepository).findAll();
    }
}
