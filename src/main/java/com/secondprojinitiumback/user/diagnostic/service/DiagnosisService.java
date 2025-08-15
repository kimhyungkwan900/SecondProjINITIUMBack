package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
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
import java.util.*;
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
    public void deleteDiagnosticTest(Long id) {
        DiagnosticTest test = testRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND));
        test.setDelYn("Y"); // 물리삭제 금지
        testRepository.save(test);
    }



    /**
     * 사용 가능한 검사 목록 조회
     * - 현재는 전체 조회(findAll) 후 DTO 변환
     */
    @Transactional(readOnly = true)
    public List<DiagnosticTestDto> getAvailableTests() {
        return testRepository.findAllByDelYn("N").stream()
                .map(DiagnosticTestDto::from)
                .toList();
    }


    /**
     * 키워드 기반 검색
     * - 이름에 키워드(대소문자 무시) 포함되는 검사 조회
     */
    @Transactional(readOnly = true)
    public List<DiagnosticTestDto> searchTestsByKeyword(String keyword) {
        return testRepository.findByNameContainingIgnoreCaseAndDelYn(keyword, "N").stream()
                .map(DiagnosticTestDto::from)
                .collect(Collectors.toList());
    }


    /**
     * 특정 검사 문항 조회
     * - 질문을 정렬(order ASC)로 가져오고, 각 질문의 보기까지 DTO로 구성
     */
    @Transactional(readOnly = true)
    public List<DiagnosticQuestionDto> getQuestionsByTestId(Long testId) {
        if (!testRepository.existsByIdAndDelYn(testId, "N")) {
            throw new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND);
        }

        return questionRepository.findByTest_IdOrderByOrderAsc(testId).stream()
                .map(q -> DiagnosticQuestionDto.builder()
                        .id(q.getId())
                        .content(q.getContent())
                        .order(q.getOrder())
                        .answerType(q.getAnswerType() != null ? q.getAnswerType().name() : null)
                        .answers(q.getAnswers().stream()
                                .map(a -> DiagnosticAnswerDto.builder()
                                        .id(a.getId())
                                        .content(a.getContent())
                                        .score(a.getScore())
                                        .selectValue(a.getSelectValue())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    /**
     * 페이징 검색
     * - 키워드로 검사명을 검색하고 Pageable로 페이징/정렬 반영
     */
    @Transactional(readOnly = true)
    public Page<DiagnosticTestDto> getPagedTests(String keyword, Pageable pageable) {
        Page<DiagnosticTest> page = testRepository.findByNameContainingIgnoreCaseAndDelYn(
                (keyword == null ? "" : keyword), "N", pageable
        );
        return page.map(t -> DiagnosticTestDto.builder()
                .id(t.getId())
                .name(t.getName())
                .description(t.getDescription())
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
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND));

        if ("Y".equals(test.getDelYn())) {
            throw new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND);
        }

        Student student = studentRepository.findByLoginInfoLoginId(loginInfo.getLoginId())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

        DiagnosticResult result = DiagnosticResult.builder()
                .test(test)
                .student(student)
                .loginInfo(loginInfo)
                .completionDate(LocalDateTime.now())
                .build();
        resultRepository.save(result);

        List<DiagnosticResultDetail> details = request.getAnswers().stream()
                .map(ans -> {
                    DiagnosticQuestion q = questionRepository.findById(ans.getQuestionId())
                            .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_QUESTION_NOT_FOUND));

                    // ✅ 정확히 매칭
                    DiagnosticAnswer selected = answerRepository
                            .findByQuestion_IdAndSelectValue(q.getId(), ans.getSelectedValue())
                            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PARAMETER));

                    return DiagnosticResultDetail.builder()
                            .result(result)
                            .question(q)
                            .selectedValue(ans.getSelectedValue())
                            .score(selected.getScore())
                            .build();
                }).toList();

        resultDetailRepository.saveAll(details);
        int totalScore = scoreService.calculateTotalScore(details);
        result.setTotalScore(totalScore);

        return result.getId();
    }

    /**
     * 결과 단건 조회 + 본인 확인
     * - 결과의 주인이 현재 로그인 사용자와 동일한지 확인
     * - 점수 해석 메시지 포함 DTO로 반환
     */
    @Transactional(readOnly = true)
    public DiagnosticResultDto getResultWithStudentCheck(Long resultId, String loginId) {
        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_RESULT_NOT_FOUND));

        String ownerLoginId = (result.getLoginInfo() != null)
                ? result.getLoginInfo().getLoginId()
                : (result.getStudent() != null && result.getStudent().getLoginInfo() != null
                ? result.getStudent().getLoginInfo().getLoginId()
                : null);
        if (!Objects.equals(ownerLoginId, loginId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        String interpreted = scoreService.interpretScore(result.getTest().getId(), result.getTotalScore());
        return DiagnosticResultDto.from(result, interpreted);
    }



    /**
     * 결과 요약 조회
     * - 결과 존재 검증 후 요약 정보와 해석 메시지 구성
     * - studentNo를 포함하여 반환
     */
    public DiagnosticResultDto getResultSummary(Long resultId) {
        DiagnosticResult result = resultRepository.findById(resultId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_RESULT_NOT_FOUND));

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
    @Transactional(readOnly = true)
    public List<DiagnosticResultDto> getAllResultsByStudent(String studentNo) {
        return resultRepository.findByStudent_StudentNo(studentNo).stream()
                .map(result -> DiagnosticResultDto.builder()
                        .resultId(result.getId())
                        .studentNo(result.getStudent().getStudentNo())
                        .testId(result.getTest().getId())
                        .testName(result.getTest().getName())
                        .totalScore(result.getTotalScore())
                        .completionDate(result.getCompletionDate())
                        .interpretedMessage(
                                scoreService.interpretScore(result.getTest().getId(), result.getTotalScore())
                        )
                        .build())
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<DiagnosticResultDto> getPagedInternalResults(String studentNo, Pageable pageable) {
        return resultRepository
                .findByStudent_StudentNoOrderByCompletionDateDesc(studentNo, pageable)
                .map(result -> DiagnosticResultDto.builder()
                        .resultId(result.getId())
                        .studentNo(result.getStudent().getStudentNo())
                        .testId(result.getTest().getId())
                        .testName(result.getTest().getName())
                        .totalScore(result.getTotalScore())
                        .completionDate(result.getCompletionDate())
                        .interpretedMessage(
                                scoreService.interpretScore(result.getTest().getId(), result.getTotalScore())
                        )
                        .build());
    }

    @Transactional
    public Long updateDiagnosticTest(Long testId, DiagnosticTestDto dto) {
        // 1) 테스트 + 자식들 로드 (answers, scoreLevels까지 fetch하는 findWithChildrenById 가정)
        DiagnosticTest test = testRepository.findWithChildrenById(testId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND));

        if ("Y".equals(test.getDelYn())) {
            throw new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND);
        }

        // 2) 기본 필드
        if (dto.getName() != null) test.setName(dto.getName());
        if (dto.getDescription() != null) test.setDescription(dto.getDescription());

        // 3) 기존 질문 맵
        Map<Long, DiagnosticQuestion> existQMap = test.getQuestions().stream()
                .filter(q -> q.getId() != null)
                .collect(Collectors.toMap(DiagnosticQuestion::getId, q -> q));

        Set<Long> incomingQIds = new HashSet<>();         // 요청에 살아있는 기존 질문 id
        List<DiagnosticQuestion> newQuestions = new ArrayList<>(); // 신규 질문 모음

        if (dto.getQuestions() != null) {
            for (DiagnosticQuestionDto qdto : dto.getQuestions()) {
                if (qdto.getId() != null && existQMap.containsKey(qdto.getId())) {
                    // ==== 기존 질문 '제자리 수정' ====
                    DiagnosticQuestion q = existQMap.get(qdto.getId());
                    incomingQIds.add(q.getId());

                    q.setContent(qdto.getContent());
                    q.setOrder(qdto.getOrder());
                    q.setAnswerType(qdto.getAnswerType() != null
                            ? AnswerType.valueOf(qdto.getAnswerType())
                            : null);

                    // ---- 보기 diff ----
                    Map<Long, DiagnosticAnswer> existAMap = q.getAnswers().stream()
                            .filter(a -> a.getId() != null)
                            .collect(Collectors.toMap(DiagnosticAnswer::getId, a -> a));

                    Set<Long> incomingAIds = new HashSet<>();
                    List<DiagnosticAnswer> rebuiltAnswers = new ArrayList<>();

                    if (qdto.getAnswers() != null) {
                        for (DiagnosticAnswerDto adto : qdto.getAnswers()) {
                            if (adto.getId() != null && existAMap.containsKey(adto.getId())) {
                                // 기존 보기 수정
                                DiagnosticAnswer a = existAMap.get(adto.getId());
                                incomingAIds.add(a.getId());
                                a.setContent(adto.getContent());
                                a.setScore(adto.getScore());
                                a.setSelectValue(adto.getSelectValue());
                                rebuiltAnswers.add(a);
                            } else {
                                // 신규 보기
                                DiagnosticAnswer a = DiagnosticAnswer.builder()
                                        .question(q)
                                        .content(adto.getContent())
                                        .score(adto.getScore())
                                        .selectValue(adto.getSelectValue())
                                        .build();
                                rebuiltAnswers.add(a);
                            }
                        }
                    }

                    // 제거될 보기들
                    List<DiagnosticAnswer> toRemoveAnswers = q.getAnswers().stream()
                            .filter(a -> a.getId() != null && !incomingAIds.contains(a.getId()))
                            .toList();
                    toRemoveAnswers.forEach(q::removeAnswer);

                    // 최종 보기 재구성
                    // (기존 리스트 clear 후 add로 구성해도 되고, set 기반으로 맞춰도 됨)
                    q.getAnswers().clear();
                    rebuiltAnswers.forEach(q::addAnswer);

                } else {
                    // ==== 신규 질문 ====
                    DiagnosticQuestion q = DiagnosticQuestion.builder()
                            .test(test)
                            .content(qdto.getContent())
                            .order(qdto.getOrder())
                            .answerType(qdto.getAnswerType() != null
                                    ? AnswerType.valueOf(qdto.getAnswerType())
                                    : null)
                            .build();

                    if (qdto.getAnswers() != null) {
                        for (DiagnosticAnswerDto adto : qdto.getAnswers()) {
                            DiagnosticAnswer a = DiagnosticAnswer.builder()
                                    .question(q)
                                    .content(adto.getContent())
                                    .score(adto.getScore())
                                    .selectValue(adto.getSelectValue())
                                    .build();
                            q.addAnswer(a);
                        }
                    }
                    newQuestions.add(q);
                }
            }
        }

        // 4) 요청에서 사라진 기존 질문 후보
        List<DiagnosticQuestion> removeCandidates = test.getQuestions().stream()
                .filter(q -> q.getId() != null && (dto.getQuestions() == null || !incomingQIds.contains(q.getId())))
                .toList();

        // 4-1) 참조 확인 후 안전한 것만 제거
        for (DiagnosticQuestion q : removeCandidates) {
            long ref = resultDetailRepository.countByQuestion_Id(q.getId());
            if (ref > 0) {
                // ✅ 과거 응답이 존재 → 삭제 금지 (그대로 두면 됨; 필요시 q.setUseYn("N") 등 소프트 숨김)
                // 예: q.setUseYn("N"); // 컬럼 있으면 사용
            } else {
                // ✅ 참조 없음 → 안전 삭제
                test.removeQuestion(q);
            }
        }

        // 4-2) 신규 질문 추가
        for (DiagnosticQuestion qNew : newQuestions) {
            test.addQuestion(qNew);
        }

        // 5) 점수 레벨은 참조가 없으므로 전체 갈아끼우기 허용
        test.getScoreLevels().clear();
        if (dto.getScoreLevels() != null) {
            for (ScoreLevelDto s : dto.getScoreLevels()) {
                DiagnosticScoreLevel lvl = DiagnosticScoreLevel.builder()
                        .test(test)
                        .minScore(s.getMinScore())
                        .maxScore(s.getMaxScore())
                        .levelName(s.getLevelName())
                        .description(s.getDescription())
                        .build();
                test.getScoreLevels().add(lvl);
            }
        }

        // 6) flush 시점에 dirty checking 반영
        return test.getId();
    }


    @Transactional(readOnly = true)
    public DiagnosticTestDto getAdminTestForEdit(Long testId) {
        var test = testRepository.findWithChildrenById(testId)
                .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND));

        questionRepository.findByTest_IdOrderByOrderAsc(testId);

        return DiagnosticTestDto.from(test);
    }

}
