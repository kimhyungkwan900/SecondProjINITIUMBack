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

/**
 * AdminResponseChoiceOptionService에 대한 단위 테스트 클래스
 * - 선택지 생성, 수정, 삭제, 조회 기능이 정상 동작하는지 검증
 */
class AdminResponseChoiceOptionServiceTest {

    // 테스트 대상 서비스
    @InjectMocks
    private AdminResponseChoiceOptionService service;

    // 의존하는 선택지 저장소 Mock
    @Mock
    private ResponseChoiceOptionRepository optionRepository;

    // 의존하는 문항 저장소 Mock
    @Mock
    private CoreCompetencyQuestionRepository questionRepository;

    // 각 테스트 실행 전 Mockito 초기화
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * [선택지 생성 테스트]
     * - 문항 ID와 선택지 DTO 리스트를 받아 저장이 2번 호출되는지 검증
     */
    @Test
    @DisplayName("선택지 생성 테스트")
    void testCreateOptions() {
        // Given
        Long questionId = 1L;
        CoreCompetencyQuestion question = CoreCompetencyQuestion.builder()
                .id(questionId)
                .description("문항1")
                .build();

        List<ResponseChoiceOptionDto> dtos = List.of(
                new ResponseChoiceOptionDto(null, "A", 1),
                new ResponseChoiceOptionDto(null, "B", 2)
        );

        // 문항 조회 Mock 설정
        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        // When
        service.createOptions(questionId, dtos);

        // Then
        // 저장 메서드가 2번 호출되어야 함
        verify(optionRepository, times(2)).save(any(ResponseChoiceOption.class));
    }

    /**
     * [선택지 수정 테스트]
     * - 기존 선택지 ID와 수정 DTO를 받아 label과 score가 변경되는지 검증
     */
    @Test
    @DisplayName("선택지 수정 테스트")
    void testUpdateOption() {
        // Given
        Long optionId = 10L;
        ResponseChoiceOption option = ResponseChoiceOption.builder()
                .id(optionId)
                .label("A")
                .score(1)
                .build();

        ResponseChoiceOptionDto dto = new ResponseChoiceOptionDto(null, "수정된 A", 5);

        // 기존 선택지 조회 Mock 설정
        when(optionRepository.findById(optionId)).thenReturn(Optional.of(option));

        // When
        service.updateOption(optionId, dto);

        // Then
        // label과 score가 변경되어야 함
        assertEquals("수정된 A", option.getLabel());
        assertEquals(5, option.getScore());
        verify(optionRepository, times(1)).save(option);
    }

    /**
     * [선택지 삭제 테스트]
     * - 선택지 ID로 삭제 메서드가 정상 호출되는지 검증
     */
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

    /**
     * [선택지 조회 테스트]
     * - 문항 ID로 조회 시 선택지 리스트가 반환되는지 검증
     */
    @Test
    @DisplayName("문항 ID로 선택지 조회 테스트")
    void testGetOptionsByQuestionId() {
        // Given
        Long questionId = 1L;
        List<ResponseChoiceOption> options = List.of(
                ResponseChoiceOption.builder().id(1L).label("A").score(1).build(),
                ResponseChoiceOption.builder().id(2L).label("B").score(2).build()
        );

        // 문항 ID로 선택지 조회 Mock 설정
        when(optionRepository.findByQuestion_Id(questionId)).thenReturn(options);

        // When
        List<ResponseChoiceOptionDto> result = service.getOptionsByQuestionId(questionId);

        // Then
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getLabel());
        assertEquals(2, result.get(1).getScore());
    }
}

