package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.*;
import com.secondprojinitiumback.admin.coreCompetency.dto.CoreCompetencyQuestionCreateDto;
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
    @Mock private SchoolSubjectRepository schoolSubjectRepository;
    @Mock private BehaviorIndicatorRepository behaviorIndicatorRepository;
    @Mock private BehaviorIndicatorMajorQuestionMappingRepository mappingRepository;

    // 공통 문항 생성 테스트
    @Test
    @DisplayName("공통 문항 생성 테스트")
    void createCommonQuestion_success() {
        // given
        Long assessmentId = 1L;
        CoreCompetencyAssessment assessment = CoreCompetencyAssessment.builder().id(assessmentId).build();
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(assessment));

        CoreCompetencyQuestionCreateDto dto = CoreCompetencyQuestionCreateDto.builder()
                .questionNo(1)
                .questionName("질문")
                .questionContent("내용")
                .displayOrder(1)
                .answerAllowCount(1)
                .isCommonCode("Y")
                .build();

        when(questionRepository.save(any(CoreCompetencyQuestion.class)))
                .thenReturn(CoreCompetencyQuestion.builder().id(1L).build());

        // when
        CoreCompetencyQuestion result = service.createCoreCompetencyQuestion(assessmentId, dto);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(questionRepository, times(1)).save(any(CoreCompetencyQuestion.class));
        verify(mappingRepository, never()).save(any());
    }

    // 비공통 -> 공통으로 수정될 때 기존 매핑 삭제 테스트
    @Test
    @DisplayName("비공통 -> 공통 수정 테스트")
    void updateQuestion_fromNotCommonToCommon_shouldDeleteMapping() {
        // given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(1L).build();
        CoreCompetencyQuestionCreateDto dto = CoreCompetencyQuestionCreateDto.builder()
                .questionNo(1)
                .questionName("수정된 질문")
                .questionContent("수정된 내용")
                .displayOrder(1)
                .answerAllowCount(1)
                .isCommonCode("Y")
                .build();

        BehaviorIndicatorMajorQuestionMapping mapping = BehaviorIndicatorMajorQuestionMapping.builder().id(1L).build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(mappingRepository.findByQuestion(question)).thenReturn(Optional.of(mapping));
        when(questionRepository.save(any())).thenReturn(question);

        // when
        CoreCompetencyQuestion result = service.updateCoreCompetencyQuestion(questionId, dto);

        // then
        assertEquals(1L, result.getId());
        verify(mappingRepository).delete(mapping);
    }

    // 공통 -> 비공통으로 수정될 때 매핑 생성 테스트
    @Test
    @DisplayName("공통 -> 비공통 수정 테스트")
    void updateQuestion_fromCommonToNotCommon_shouldCreateMapping() {
        // given
        Long questionId = 2L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(2L).build();
        CoreCompetencyQuestionCreateDto dto = CoreCompetencyQuestionCreateDto.builder()
                .questionNo(2)
                .questionName("수정된 질문")
                .questionContent("수정된 내용")
                .displayOrder(1)
                .answerAllowCount(1)
                .isCommonCode("N")
                .subjectCode("CS101")
                .indicatorId(1L)
                .build();

        BehaviorIndicator behaviorIndicator = new BehaviorIndicator();
        SchoolSubject schoolSubject = mock(SchoolSubject.class);

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(questionRepository.save(any())).thenReturn(question);
        when(behaviorIndicatorRepository.findById(1L)).thenReturn(Optional.of(behaviorIndicator));
        when(schoolSubjectRepository.findByCode("CS101")).thenReturn(Optional.of(schoolSubject));
        when(mappingRepository.findByQuestionAndBehaviorIndicatorAndSchoolSubject(question, behaviorIndicator, schoolSubject))
                .thenReturn(null);

        // when
        CoreCompetencyQuestion result = service.updateCoreCompetencyQuestion(questionId, dto);

        // then
        assertEquals(2L, result.getId());
        verify(mappingRepository).save(any());
    }

    // 문항 삭제만 수행되는 경우 테스트
    @Test
    @DisplayName("문항 삭제 테스트")
    void deleteQuestion_success() {
        // given
        Long questionId = 11L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // when
        service.deleteCoreCompetencyQuestion(questionId);

        // then
        verify(questionRepository).delete(question);
    }

    // 문항 삭제 시 매핑도 함께 삭제되는 경우 테스트
    @Test
    @DisplayName("문항 삭제 시 매핑도 함께 삭제되는 테스트")
    void deleteCoreCompetencyQuestion_shouldDeleteMappingAndQuestion() {
        // given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        BehaviorIndicatorMajorQuestionMapping mapping = BehaviorIndicatorMajorQuestionMapping.builder().build();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));
        when(mappingRepository.findByQuestion(question)).thenReturn(Optional.of(mapping));

        // when
        service.deleteCoreCompetencyQuestion(questionId);

        // then
        verify(mappingRepository).delete(mapping);
        verify(questionRepository).delete(question);
    }

    // 문항 상세 조회 실패 (예외 발생) 테스트
    @Test
    @DisplayName("존재하지 않는 문항 조회 시 예외")
    void getQuestion_notFound() {
        // given
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());

        // expect
        assertThrows(IllegalArgumentException.class, () -> service.getCoreCompetencyQuestion(99L));
    }

    // 문항 상세 조회 성공 테스트
    @Test
    @DisplayName("문항 상세 조회 테스트")
    void getQuestion() {
        // given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).build();
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // when
        CoreCompetencyQuestion result = service.getCoreCompetencyQuestion(questionId);

        // then
        assertNotNull(result);
        assertEquals(questionId, result.getId());
        verify(questionRepository).findById(questionId);
    }

    // 전체 문항 조회 테스트
    @Test
    @DisplayName("전체 문항 조회 테스트")
    void getAllQuestions() {
        // given
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        List<CoreCompetencyQuestion> result = service.getAllCoreCompetencyQuestions();

        // then
        assertTrue(result.isEmpty());
        verify(questionRepository).findAll();
    }
}
