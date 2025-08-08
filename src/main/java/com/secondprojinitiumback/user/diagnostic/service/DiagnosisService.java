package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.user.diagnostic.domain.*;
import com.secondprojinitiumback.user.diagnostic.dto.*;
import com.secondprojinitiumback.user.diagnostic.repository.*;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
    private final DiagnosticResultRepository diagnosticResultRepository;
    private final DiagnosisScoreService diagnosisScoreService;

    /**
     * 진단검사 등록
     * - DTO → 엔티티 변환
     * - 문항/보기/점수해석을 함께 구성하여 저장
     * - 연관관계 주인 설정에 주의(Question.test, Answer.question, ScoreLevel.test)
     */
    public Long registerDiagnosticTest(DiagnosticTestDto dto) {
        DiagnosticTest test = DiagnosticTest.builder()
                .name(dto.getName())                // 검사명 세팅
                .description(dto.getDescription())  // 검사 설명 세팅
                .build();

        // 문항 추가: DTO의 질문 리스트를 엔티티로 변환 후 검사에 연결
        List<DiagnosticQuestion> questions = new ArrayList<>();
        for (DiagnosticQuestionDto questionDto : dto.getQuestions()) {
            DiagnosticQuestion question = DiagnosticQuestion.builder()
                    .test(test)                                            // 연관관계 설정(Question → Test)
                    .content(questionDto.getContent())                     // 질문 내용
                    .order(questionDto.getOrder())                         // 출력/정렬 순서
                    .answerType(AnswerType.valueOf(questionDto.getAnswerType())) // 응답 유형(ENUM 문자열→ENUM)
                    .build();

            // 보기(Answers) 변환: 각 보기마다 Question을 소유주로 연결
            List<DiagnosticAnswer> answers = questionDto.getAnswers().stream()
                    .map(answerDto -> DiagnosticAnswer.builder()
                            .question(question)                 // 연관관계 설정(Answer → Question)
                            .content(answerDto.getContent())    // 보기 텍스트
                            .score(answerDto.getScore())        // 보기 점수
                            .selectValue(answerDto.getSelectValue()) // 프론트에서 선택한 value와 매칭될 값
                            .build())
                    .toList();

            question.setAnswers(answers);      // 양방향 편의 메서드 없이 컬렉션 직접 주입
            questions.add(question);           // 최종 질의 리스트에 포함
        }

        // 점수 해석 추가
        List<DiagnosticScoreLevel> scoreLevels = dto.getScoreLevels().stream()
                .map(levelDto -> DiagnosticScoreLevel.builder()
                        .test(test)                             // 연관관계 설정(ScoreLevel → Test)
                        .minScore(levelDto.getMinScore())       // 최소 점수
                        .maxScore(levelDto.getMaxScore())       // 최대 점수
                        .levelName(levelDto.getLevelName())     // 등급명
                        .description(levelDto.getDescription()) // 등급 설명
                        .build())
                .toList();

        test.setQuestions(questions);           // Test에 문항 세팅
        test.setScoreLevels(scoreLevels);       // Test에 점수해석 세팅

        return testRepository.save(test).getId(); // 영속화 후 생성된 PK 반환
    }

    /**
     * 진단검사 삭제
     * - 참조 무결성을 위해 삭제 순서 중요(상세 → 결과 → 보기 → 질문 → 검사)
     * - FK 제약으로 인한 삭제 실패를 방지
     */
    @Transactional
    public void deleteDiagnosticTest(Long id) {
        // 존재 확인: 없는 경우 400 범주 예외 던짐
        DiagnosticTest test = testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 검사가 존재하지 않습니다."));

        // 순서 중요: 결과상세 → 결과 → 보기 → 질문 → 검사
        resultDetailRepository.deleteDetailsByTestId(id); // 결과 상세 먼저 삭제
        resultRepository.deleteResultsByTestId(id);       // 결과 삭제
        answerRepository.deleteAnswersByTestId(id);       // 보기 삭제
        questionRepository.deleteQuestionsByTestId(id);   // 질문 삭제

        testRepository.delete(test); // 마지막으로 검사 삭제
    }



    /**
     * 사용 가능한 검사 목록 조회
     * - 현재는 전체 조회(findAll) 후 DTO 변환
     */
    public List<DiagnosticTestDto> getAvailableTests() {
        return testRepository.findAll().stream() // 전체 검사 조회
                .map(DiagnosticTestDto::from)    // 엔티티 → DTO 매핑
                .toList();                       // 리스트로 변환
    }


    /**
     * 키워드 기반 검색
     * - 이름에 키워드(대소문자 무시) 포함되는 검사 조회
     */
    public List<DiagnosticTestDto> searchTestsByKeyword(String keyword) {
        List<DiagnosticTest> tests = testRepository
                .findByNameContainingIgnoreCase(keyword); // 키워드 검색
        return tests.stream()
                .map(DiagnosticTestDto::from)
                .collect(Collectors.toList());
    }


    /**
     * 특정 검사 문항 조회
     * - 질문을 정렬(order ASC)로 가져오고, 각 질문의 보기까지 DTO로 구성
     */
    public List<DiagnosticQuestionDto> getQuestionsByTestId(Long testId) {
        return questionRepository.findByTestIdOrderByOrderAsc(testId).stream()
                .map(q -> DiagnosticQuestionDto.builder()
                        .id(q.getId())                              // 질문 ID
                        .content(q.getContent())                    // 질문 내용
                        .order(q.getOrder())                        // 질문 순서
                        .answerType(q.getAnswerType().name())       // ENUM → 문자열
                        .answers(q.getAnswers().stream().map(a -> DiagnosticAnswerDto.builder()  // 보기 리스트 DTO 매핑
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
     * - 키워드로 검사명을 검색하고 Pageable로 페이징/정렬 반영
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
     * 검사 제출 (studentNo가 아닌 loginId로 Student 조회)
     * - 로그인 사용자를 기반으로 Student를 조회하여 결과에 귀속
     * - 응답값(selectedValue)과 보기(selectValue)를 매칭해 점수 계산
     * - 결과 및 상세 저장 후 총점 세팅
     */
    public Long submitDiagnosis(DiagnosisSubmitRequestDto request, LoginInfo loginInfo) {

        DiagnosticTest test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new IllegalArgumentException("검사를 찾을 수 없습니다."));

        // studentNo 기반이 아니라 loginId로 Student 조회 (요구사항에 맞춘 연동)
        Student student = studentRepository.findByLoginInfoLoginId(loginInfo.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        // 결과 요약 엔티티 생성 및 저장(선 저장 → 상세에서 FK 참조)
        DiagnosticResult result = DiagnosticResult.builder()
                .test(test)
                .student(student) // 🔹 userId 대신 Student 엔티티 저장
                .loginInfo(loginInfo)
                .completionDate(LocalDateTime.now())
                .build();
        resultRepository.save(result);

        // 상세 응답 매핑: 사용자가 제출한 각 문항의 selectedValue를 기준으로 보기 점수 찾기
        List<DiagnosticResultDetail> details = request.getAnswers().stream()
                .map(answer -> {
                    DiagnosticQuestion question = questionRepository.findById(answer.getQuestionId())
                            .orElseThrow(() -> new IllegalArgumentException("문항을 찾을 수 없습니다."));

                    // 같은 문항의 보기 중 selectValue == 사용자가 선택한 값인 항목 찾기
                    DiagnosticAnswer selectedAnswer = answerRepository.findByQuestionId(question.getId()).stream()
                            .filter(a -> Objects.equals(a.getSelectValue(), answer.getSelectedValue()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("응답이 유효하지 않습니다."));

                    return DiagnosticResultDetail.builder()
                            .result(result)                               // 상위 결과 FK
                            .question(question)                           // 어떤 문항인지
                            .selectedValue(answer.getSelectedValue())     // 사용자가 선택한 값(추적용)
                            .score(selectedAnswer.getScore())             // 해당 보기의 점수
                            .build();
                }).toList();

        resultDetailRepository.saveAll(details);              // 상세 일괄 저장
        int totalScore = scoreService.calculateTotalScore(details); // 총점 계산(서비스에 위임)
        result.setTotalScore(totalScore);                     // 결과 엔티티에 총점 반영(영속 상태라 flush 시 반영)

        return result.getId();                                // 생성된 결과 ID 반환
    }

    /**
     * 결과 단건 조회 + 본인 확인
     * - 결과의 주인이 현재 로그인 사용자와 동일한지 확인
     * - 점수 해석 메시지 포함 DTO로 반환
     */
    public DiagnosticResultDto getResultWithStudentCheck(Long resultId, String loginId) {
        // 1. 결과 조회
        DiagnosticResult result = diagnosticResultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("검사 결과가 존재하지 않습니다."));

        // 2. 검사 결과의 주인 확인
        String resultOwnerLoginId = result.getStudent().getLoginInfo().getLoginId();
        if (!resultOwnerLoginId.equals(loginId)) {
            throw new AccessDeniedException("본인의 검사 결과만 조회할 수 있습니다.");
        }

        // 3. 점수 해석 메시지 계산
        String interpreted = diagnosisScoreService.interpretScore(
                result.getTest().getId(),
                result.getTotalScore()
        );

        // 4. DTO 반환
        return DiagnosticResultDto.from(result, interpreted);
    }



    /**
     * 결과 요약 조회
     * - 결과 존재 검증 후 요약 정보와 해석 메시지 구성
     * - studentNo를 포함하여 반환
     */
    public DiagnosticResultDto getResultSummary(Long resultId) {
        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("결과 없음"));

        String interpreted = scoreService.interpretScore(result.getTest().getId(), result.getTotalScore());

        return DiagnosticResultDto.builder()
                .resultId(result.getId())
                .studentNo(result.getStudent().getStudentNo()) // 🔹 studentNo 사용
                .testId(result.getTest().getId())
                .testName(result.getTest().getName())
                .totalScore(result.getTotalScore())
                .completionDate(result.getCompletionDate())
                .interpretedMessage(interpreted)
                .build();
    }

    /**
     * 특정 학생의 전체 결과 목록 조회
     * - studentNo로 필터링하여 본인 결과만 모아 반환
     * - 각 결과마다 해석 메시지를 동적으로 계산
     */
    public List<DiagnosticResultDto> getAllResultsByStudent(String studentNo) {
        return resultRepository.findByStudent_StudentNo(studentNo).stream()
                .map(result -> DiagnosticResultDto.builder()
                        .resultId(result.getId())
                        .studentNo(result.getStudent().getStudentNo())
                        .testId(result.getTest().getId())
                        .testName(result.getTest().getName())
                        .totalScore(result.getTotalScore())
                        .completionDate(result.getCompletionDate())
                        .interpretedMessage(scoreService.interpretScore(result.getTest().getId(), result.getTotalScore()))
                        .build())
                .toList();
    }

}
