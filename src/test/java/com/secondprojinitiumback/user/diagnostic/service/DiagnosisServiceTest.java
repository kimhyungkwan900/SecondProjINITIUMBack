package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticAnswer;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticQuestion;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import com.secondprojinitiumback.user.diagnostic.dto.*;
import com.secondprojinitiumback.user.diagnostic.repository.*;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DiagnosisServiceTest {
    @Mock
    private DiagnosticTestRepository testRepository;
    @Mock
    private DiagnosticQuestionRepository questionRepository;
    @Mock
    private DiagnosticAnswerRepository answerRepository;
    @Mock
    private DiagnosticResultRepository resultRepository;
    @Mock
    private DiagnosticResultDetailRepository resultDetailRepository;
    @Mock
    private DiagnosisScoreService scoreService;
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private DiagnosisService diagnosisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 검사 등록 테스트
     */
    @Test
    void registerDiagnosticTest_성공() {
        // given
        DiagnosticTestDto dto = DiagnosticTestDto.builder()
                .name("Test Name")
                .description("Desc")
                .useYn(true)
                .questions(List.of(
                        DiagnosticQuestionDto.builder()
                                .content("Q1")
                                .order(1)
                                .answerType("YES_NO")
                                .answers(List.of(
                                        DiagnosticAnswerDto.builder()
                                                .content("Yes")
                                                .score(10)
                                                .selectValue(1)
                                                .build()
                                ))
                                .build()
                ))
                .build();

        DiagnosticTest mockTest = DiagnosticTest.builder().id(1L).name("Test Name").build();
        when(testRepository.save(any(DiagnosticTest.class))).thenReturn(mockTest);

        // when
        Long id = diagnosisService.registerDiagnosticTest(dto);

        // then
        assertThat(id).isEqualTo(1L);
        verify(testRepository, times(1)).save(any(DiagnosticTest.class));
    }

    /**
     * 사용 가능한 검사 조회
     */
    @Test
    void getAvailableTests_성공() {
        // given
        when(testRepository.findByUseYn("Y")).thenReturn(
                List.of(DiagnosticTest.builder().id(1L).name("Test1").description("Desc1").build())
        );

        // when
        List<DiagnosticTestDto> result = diagnosisService.getAvailableTests();

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test1");
    }

    /**
     * 페이징 검색
     */
    @Test
    void getPagedTests_성공() {
        // given
        Page<DiagnosticTest> page = new PageImpl<>(
                List.of(DiagnosticTest.builder().id(1L).name("PagedTest").description("PagedDesc").build())
        );
        when(testRepository.findByNameContainingIgnoreCase(eq("Test"), any(PageRequest.class))).thenReturn(page);

        // when
        Page<DiagnosticTestDto> result = diagnosisService.getPagedTests("Test", PageRequest.of(0, 10));

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("PagedTest");
    }

    /**
     * 검사 제출
     */
    @Test
    void submitDiagnosis_성공() {
        // given
        DiagnosisSubmitRequestDto request = DiagnosisSubmitRequestDto.builder()
                .testId(1L)
                .studentNo("20250001")
                .answers(List.of(
                        DiagnosisSubmitRequestDto.AnswerSubmission.builder()
                                .questionId(10L)
                                .selectedValue(1)
                                .build()
                ))
                .build();

        DiagnosticTest test = DiagnosticTest.builder().id(1L).build();
        Student student = Student.builder().studentNo("20250001").build();
        DiagnosticQuestion question = DiagnosticQuestion.builder().id(10L).build();
        DiagnosticAnswer answer = DiagnosticAnswer.builder().id(100L).score(5).selectValue(1).build();

        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        when(studentRepository.findById("20250001")).thenReturn(Optional.of(student));
        when(questionRepository.findById(10L)).thenReturn(Optional.of(question));
        when(answerRepository.findByQuestionId(10L)).thenReturn(List.of(answer));
        when(scoreService.calculateTotalScore(anyList())).thenReturn(5);

        // when
        Long resultId = diagnosisService.submitDiagnosis(request);

        // then
        assertThat(resultId).isNotNull();
        verify(resultRepository, times(1)).save(any(DiagnosticResult.class));
    }

    /**
     * 결과 요약 조회
     */
    @Test
    void getResultSummary_성공() {
        // given
        DiagnosticResult result = DiagnosticResult.builder()
                .id(1L)
                .test(DiagnosticTest.builder().id(1L).build())
                .student(Student.builder().studentNo("20250001").build())
                .totalScore(90)
                .completionDate(LocalDateTime.now())
                .build();

        when(resultRepository.findById(1L)).thenReturn(Optional.of(result));
        when(scoreService.interpretScore(1L, 90)).thenReturn("상 - 매우 우수");

        // when
        DiagnosticResultDto dto = diagnosisService.getResultSummary(1L);

        // then
        assertThat(dto.getInterpretedMessage()).isEqualTo("상 - 매우 우수");
        assertThat(dto.getStudentNo()).isEqualTo("20250001");
    }
}