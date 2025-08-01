package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.domain.*;
import com.secondprojinitiumback.user.diagnostic.dto.*;
import com.secondprojinitiumback.user.diagnostic.repository.*;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisService {

    private final DiagnosticTestRepository testRepository;
    private final DiagnosticQuestionRepository questionRepository;
    private final DiagnosticAnswerRepository answerRepository;
    private final DiagnosticResultRepository resultRepository;
    private final DiagnosticResultDetailRepository resultDetailRepository;
    private final DiagnosisScoreService scoreService;
    private final StudentRepository studentRepository;

    /**
     * 진단검사 등록
     */
    public Long registerDiagnosticTest(DiagnosticTestDto dto) {
        DiagnosticTest test = DiagnosticTest.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .useYn(Boolean.TRUE.equals(dto.getUseYn()) ? "Y" : "N")
                .build();

        List<DiagnosticQuestion> questions = new ArrayList<>();

        for (DiagnosticQuestionDto questionDto : dto.getQuestions()) {
            DiagnosticQuestion question = DiagnosticQuestion.builder()
                    .test(test)
                    .content(questionDto.getContent())
                    .order(questionDto.getOrder())
                    .answerType(AnswerType.valueOf(questionDto.getAnswerType()))
                    .build();

            List<DiagnosticAnswer> answers = questionDto.getAnswers().stream()
                    .map(answerDto -> DiagnosticAnswer.builder()
                            .question(question)
                            .content(answerDto.getContent())
                            .score(answerDto.getScore())
                            .selectValue(answerDto.getSelectValue())
                            .build())
                    .toList();

            question.setAnswers(answers);
            questions.add(question);
        }

        test.setQuestions(questions);
        return testRepository.save(test).getId();
    }

    @Transactional
    public void deleteDiagnosticTest(Long id) {
        if (!testRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제할 검사가 존재하지 않습니다.");
        }
        testRepository.deleteById(id);
    }


    /**
     * 사용 가능한 검사 목록 조회
     */
    public List<DiagnosticTestDto> getAvailableTests() {
        return testRepository.findByUseYn("Y").stream()
                .map(test -> DiagnosticTestDto.builder()
                        .id(test.getId())
                        .name(test.getName())
                        .description(test.getDescription())
                        .build())
                .toList();
    }

    /**
     * 키워드 기반 검색
     */
    // DiagnosisService.java
    public List<DiagnosticTestDto> searchTestsByKeyword(String keyword) {
        List<DiagnosticTest> tests = testRepository
                .findByNameContainingIgnoreCaseAndUseYn(keyword, "Y"); // 🔹 "Y"로 명시
        return tests.stream()
                .map(DiagnosticTestDto::from)
                .collect(Collectors.toList());
    }


    /**
     * 특정 검사 문항 조회
     */
    public List<DiagnosticQuestionDto> getQuestionsByTestId(Long testId) {
        return questionRepository.findByTestIdOrderByOrderAsc(testId).stream()
                .map(q -> DiagnosticQuestionDto.builder()
                        .id(q.getId())
                        .content(q.getContent())
                        .order(q.getOrder())
                        .answerType(q.getAnswerType().name())
                        .answers(q.getAnswers().stream().map(a -> DiagnosticAnswerDto.builder()
                                .id(a.getId())
                                .content(a.getContent())
                                .score(a.getScore())
                                .selectValue(a.getSelectValue())
                                .build()).toList())
                        .build())
                .toList();
    }

    /**
     * 페이징 검색
     */
    public Page<DiagnosticTestDto> getPagedTests(String keyword, Pageable pageable) {
        Page<DiagnosticTest> page = testRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return page.map(test -> DiagnosticTestDto.builder()
                .id(test.getId())
                .name(test.getName())
                .description(test.getDescription())
                .build());
    }

    /**
     * 검사 제출 (studentNo 기반 저장)
     */
    public Long submitDiagnosis(DiagnosisSubmitRequestDto request) {
        DiagnosticTest test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new IllegalArgumentException("검사를 찾을 수 없습니다."));

        // 🔹 studentNo 기반 Student 조회
        Student student = studentRepository.findById(request.getStudentNo())
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        DiagnosticResult result = DiagnosticResult.builder()
                .test(test)
                .student(student) // 🔹 userId 대신 Student 엔티티 저장
                .completionDate(LocalDateTime.now())
                .build();
        resultRepository.save(result);

        List<DiagnosticResultDetail> details = request.getAnswers().stream()
                .map(answer -> {
                    DiagnosticQuestion question = questionRepository.findById(answer.getQuestionId())
                            .orElseThrow(() -> new IllegalArgumentException("문항을 찾을 수 없습니다."));

                    DiagnosticAnswer selectedAnswer = answerRepository.findByQuestionId(question.getId()).stream()
                            .filter(a -> Objects.equals(a.getSelectValue(), answer.getSelectedValue()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("응답이 유효하지 않습니다."));

                    return DiagnosticResultDetail.builder()
                            .result(result)
                            .question(question)
                            .selectedValue(answer.getSelectedValue())
                            .score(selectedAnswer.getScore())
                            .build();
                }).toList();

        resultDetailRepository.saveAll(details);
        int totalScore = scoreService.calculateTotalScore(details);
        result.setTotalScore(totalScore);

        return result.getId();
    }

    /**
     * 결과 요약 조회 (studentNo 반환)
     */
    public DiagnosticResultDto getResultSummary(Long resultId) {
        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("결과 없음"));

        String interpreted = scoreService.interpretScore(result.getTest().getId(), result.getTotalScore());

        return DiagnosticResultDto.builder()
                .resultId(result.getId())
                .studentNo(result.getStudent().getStudentNo()) // 🔹 studentNo 사용
                .testId(result.getTest().getId())
                .totalScore(result.getTotalScore())
                .completionDate(result.getCompletionDate())
                .interpretedMessage(interpreted)
                .build();
    }

    public List<DiagnosticResultDto> getAllResultsByStudent(String studentNo) {
        return resultRepository.findByStudent_StudentNo(studentNo).stream()
                .map(result -> DiagnosticResultDto.builder()
                        .resultId(result.getId())
                        .studentNo(result.getStudent().getStudentNo())
                        .testId(result.getTest().getId())
                        .totalScore(result.getTotalScore())
                        .completionDate(result.getCompletionDate())
                        .interpretedMessage(scoreService.interpretScore(result.getTest().getId(), result.getTotalScore()))
                        .build())
                .toList();
    }

}
