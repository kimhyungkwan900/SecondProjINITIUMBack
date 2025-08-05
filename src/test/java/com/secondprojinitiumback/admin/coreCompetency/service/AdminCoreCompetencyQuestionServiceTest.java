package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionDto;
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
// CoreCompetencyQuestion 관련 서비스 기능 단위 테스트 클래스
class AdminCoreCompetencyQuestionServiceTest {

    @InjectMocks
    private AdminCoreCompetencyQuestionService service;

    @Mock private CoreCompetencyQuestionRepository questionRepository;
    @Mock private CoreCompetencyAssessmentRepository assessmentRepository;
    @Mock private SchoolSubjectRepository schoolSubjectRepository;
    @Mock private BehaviorIndicatorRepository behaviorIndicatorRepository;
    @Mock private BehaviorIndicatorMajorQuestionMappingRepository mappingRepository;

    // [1] 공통 문항 생성 테스트
    @Test
    @DisplayName("공통 문항 생성 테스트")
    void createCommonQuestion_success() {
        // Given
        Long assessmentId = 1L;
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder().id(assessmentId).build();
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));

        CoreCompetencyQuestionDto dto = CoreCompetencyQuestionDto.builder()
                .questionNo(1)
                .questionName("질문")
                .questionContent("내용")
                .displayOrder(1)
                .answerAllowCount(1)
                .isCommonCode("Y") // 공통 문항
                .build();

        when(questionRepository.save(any(CoreCompetencyQuestion.class)))
                .thenReturn(CoreCompetencyQuestion.builder().id(1L).build());

        // When
        CoreCompetencyQuestion result = service.createCoreCompetencyQuestion(assessmentId, dto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(questionRepository, times(1)).save(any(CoreCompetencyQuestion.class));
        verify(mappingRepository, never()).save(any()); // 공통 문항은 매핑 없음
    }

    // [2] 비공통 → 공통 수정 시 기존 매핑 삭제되는지 확인
    @Test
    @DisplayName("비공통 → 공통 수정 테스트")
    void updateQuestion_fromNotCommonToCommon_shouldDeleteMapping() {
        // Given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(1L).build();
        BehaviorIndicatorMajorQuestionMapping mapping = BehaviorIndicatorMajorQuestionMapping.builder().id(1L).build();

        CoreCompetencyQuestionDto dto = CoreCompetencyQuestionDto.builder()
                .questionNo(1)
                .questionName("수정된 질문")
                .questionContent("수정된 내용")
                .displayOrder(1)
                .answerAllowCount(1)
                .isCommonCode("Y") // 공통으로 변경됨
                .build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(mappingRepository.findByQuestion(question)).thenReturn(Optional.of(mapping));
        when(questionRepository.save(any())).thenReturn(question);

        // When
        CoreCompetencyQuestion result = service.updateCoreCompetencyQuestion(questionId, dto);

        // Then
        assertEquals(1L, result.getId());
        verify(mappingRepository).delete(mapping); // 기존 매핑 삭제되어야 함
    }

    // [3] 공통 → 비공통으로 수정될 때 새로운 매핑 생성 테스트
    @Test
    @DisplayName("공통 → 비공통 수정 테스트")
    void updateQuestion_fromCommonToNotCommon_shouldCreateMapping() {
        // Given
        Long questionId = 2L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(2L).build();
        BehaviorIndicator indicator = new BehaviorIndicator();
        SchoolSubject subject = mock(SchoolSubject.class);

        CoreCompetencyQuestionDto dto = CoreCompetencyQuestionDto.builder()
                .questionNo(2)
                .questionName("수정된 질문")
                .questionContent("수정된 내용")
                .displayOrder(1)
                .answerAllowCount(1)
                .isCommonCode("N") // 비공통으로 변경됨
                .subjectCode("CS101")
                .indicatorId(1L)
                .build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionRepository.save(any())).thenReturn(question);
        when(behaviorIndicatorRepository.findById(1L)).thenReturn(Optional.of(indicator));
        when(schoolSubjectRepository.findBySubjectCode("CS101")).thenReturn(Optional.of(subject));
        when(mappingRepository.findByQuestionAndBehaviorIndicatorAndSchoolSubject(question, indicator, subject))
                .thenReturn(null); // 기존 매핑 없음

        // When
        CoreCompetencyQuestion result = service.updateCoreCompetencyQuestion(questionId, dto);

        // Then
        assertEquals(2L, result.getId());
        verify(mappingRepository).save(any()); // 새로운 매핑 저장
    }

    // [4] 문항 단순 삭제 테스트 (매핑 없음)
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

    // [5] 문항 삭제 시 매핑도 함께 삭제되는 경우 테스트
    @Test
    @DisplayName("문항 삭제 시 매핑도 함께 삭제되는 테스트")
    void deleteCoreCompetencyQuestion_shouldDeleteMappingAndQuestion() {
        // Given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        BehaviorIndicatorMajorQuestionMapping mapping = BehaviorIndicatorMajorQuestionMapping.builder().build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(mappingRepository.findByQuestion(question)).thenReturn(Optional.of(mapping));

        // When
        service.deleteCoreCompetencyQuestion(questionId);

        // Then
        verify(mappingRepository).delete(mapping);
        verify(questionRepository).delete(question);
    }

    // [6] 존재하지 않는 문항 조회 시 예외 발생
    @Test
    @DisplayName("존재하지 않는 문항 조회 시 예외")
    void getQuestion_notFound() {
        // Given
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());

        // Then + When
        assertThrows(IllegalArgumentException.class, () -> service.getCoreCompetencyQuestion(99L));
    }

    // [7] 문항 상세 조회 테스트
    @Test
    @DisplayName("문항 상세 조회 테스트")
    void getQuestion() {
        // Given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // When
        CoreCompetencyQuestion result = service.getCoreCompetencyQuestion(questionId);

        // Then
        assertNotNull(result);
        assertEquals(questionId, result.getId());
        verify(questionRepository).findById(questionId);
    }

    // [8] 전체 문항 리스트 조회 테스트
    @Test
    @DisplayName("전체 문항 조회 테스트")
    void getAllQuestions() {
        // Given
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<CoreCompetencyQuestion> result = service.getAllCoreCompetencyQuestions();

        // Then
        assertTrue(result.isEmpty());
        verify(questionRepository).findAll();
    }
}

