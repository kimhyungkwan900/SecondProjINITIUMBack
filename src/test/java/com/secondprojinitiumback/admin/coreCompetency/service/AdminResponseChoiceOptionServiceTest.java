package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import com.secondprojinitiumback.admin.coreCompetency.dto.ResponseChoiceOptionDto;
import com.secondprojinitiumback.admin.coreCompetency.repository.CoreCompetencyQuestionRepository;
import com.secondprojinitiumback.admin.coreCompetency.repository.ResponseChoiceOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminResponseChoiceOptionServiceTest {

    @InjectMocks
    private AdminResponseChoiceOptionService service;

    @Mock
    private ResponseChoiceOptionRepository optionRepository;

    @Mock
    private CoreCompetencyQuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("선택지 생성 테스트")
    void testCreateOptions() {
        // Given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder().id(questionId).description("문항1").build();

        List<ResponseChoiceOptionDto> dtos = List.of(
                new ResponseChoiceOptionDto(null, "A", 1),
                new ResponseChoiceOptionDto(null, "B", 2)
        );

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // When
        service.createOptions(questionId, dtos);

        // Then
        verify(optionRepository, times(2)).save(any(ResponseChoiceOption.class));
    }

    @Test
    @DisplayName("선택지 수정 테스트")
    void testUpdateOption() {
        // Given
        Long optionId = 10L;
        ResponseChoiceOption option = ResponseChoiceOption.builder().id(optionId).label("A").score(1).build();

        ResponseChoiceOptionDto dto = new ResponseChoiceOptionDto(null, "수정된 A", 5);

        when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));

        // When
        service.updateOption(optionId, dto);

        // Then
        assertEquals("수정된 A", option.getLabel());
        assertEquals(5, option.getScore());
        verify(optionRepository, times(1)).save(option);
    }

    @Test
    @DisplayName("선택지 삭제 테스트")
    void testDeleteOption() {
        // Given
        Long optionId = 99L;

        // When
        service.deleteOption(optionId);

        // Then
        verify(optionRepository, times(1)).deleteById(optionId);
    }

    @Test
    @DisplayName("문항 ID로 선택지 조회 테스트")
    void testGetOptionsByQuestionId() {
        // Given
        Long questionId = 1L;
        List<ResponseChoiceOption> options = List.of(
                ResponseChoiceOption.builder().id(1L).label("A").score(1).build(),
                ResponseChoiceOption.builder().id(2L).label("B").score(2).build()
        );

        when(optionRepository.findByQuestion_Id(questionId)).thenReturn(options);

        // When
        List<ResponseChoiceOptionDto> result = service.getOptionsByQuestionId(questionId);

        // Then
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getLabel());
        assertEquals(2, result.get(1).getScore());
    }
}
