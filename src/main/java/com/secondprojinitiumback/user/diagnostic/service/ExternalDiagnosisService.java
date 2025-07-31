package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticResult;
import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticTest;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisRequestDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalDiagnosisResultDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalQuestionResponseDto;
import com.secondprojinitiumback.user.diagnostic.dto.ExternalTestListDto;
import com.secondprojinitiumback.user.diagnostic.repository.ExternalDiagnosticResultRepository;
import com.secondprojinitiumback.user.diagnostic.repository.ExternalDiagnosticTestRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository; // StudentRepository 추가
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExternalDiagnosisService {

    private final ExternalDiagnosticTestRepository testRepository;
    private final ExternalDiagnosticResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;

    @Value("${career.api.key}")
    private String apiKey;

    @Value("${career.api.questions-url}")
    private String questionUrl;

    @Value("${career.api.report-url}")
    private String reportUrl;

    /**
     * 외부 진단검사 전체 목록 조회
     */
    public List<ExternalTestListDto> getAvailableExternalTests() {
        return testRepository.findAll().stream()
                .map(ExternalTestListDto::from)
                .toList();
    }

    /**
     * 외부 진단검사 이름 검색
     */
    public List<ExternalTestListDto> searchExternalTestsByName(String keyword) {
        return testRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(ExternalTestListDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 페이징 처리된 외부 진단검사 검색
     */
    public Page<ExternalTestListDto> getPagedExternalTests(String keyword, Pageable pageable) {
        Page<ExternalDiagnosticTest> page = testRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return page.map(ExternalTestListDto::from);
    }

    /**
     * 1. 외부 진단 문항 조회 - 원본 응답 Map
     */
    public Map<String, Object> fetchExternalQuestions(String qestrnSeq, String trgetSe, String apiKey) {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(questionUrl)
                .queryParam("apikey", apiKey)
                .queryParam("q", qestrnSeq)
                .build();

        return restTemplate.getForObject(uri.toUri(), Map.class);
    }

    /**
     * 2. 외부 진단 문항 조회 - 정제된 응답 DTO
     */
    public ExternalQuestionResponseDto getParsedExternalQuestions(String qestrnSeq, String trgetSe, String apiKey) {
        Map<String, Object> raw = fetchExternalQuestions(qestrnSeq, trgetSe, apiKey);

        String title = (String) raw.getOrDefault("qestrnTitle", "제목 없음");
        String description = (String) raw.getOrDefault("qestrnDesc", "");

        List<Map<String, Object>> questionList = (List<Map<String, Object>>) raw.get("questions");

        List<ExternalQuestionResponseDto.QuestionItem> questions = questionList.stream()
                .map(q -> ExternalQuestionResponseDto.QuestionItem.builder()
                        .questionText((String) q.get("question"))
                        .options((List<String>) q.get("options"))
                        .build())
                .toList();

        return ExternalQuestionResponseDto.builder()
                .title(title)
                .description(description)
                .questions(questions)
                .build();
    }

    /**
     * 3. 외부 진단 검사 결과 제출 및 저장
     */
    public ExternalDiagnosisResultDto submitExternalResult(ExternalDiagnosisRequestDto dto, String apiKey) {

        // Request Body 생성
        Map<String, Object> body = new HashMap<>();
        body.put("apikey", apiKey);
        body.put("qestrnSeq", dto.getQestrnSeq());
        body.put("trgetSe", dto.getTrgetSe());
        body.put("gender", dto.getGender());

        // 학교 / 학년 (값이 있으면 세팅)
        if (dto.getSchool() != null) body.put("school", dto.getSchool());
        if (dto.getGrade() != null) body.put("grade", dto.getGrade());

        // 시작일시 (null이면 서버에서 자동 세팅)
        String startTimestamp = dto.getStartDtm() != null
                ? dto.getStartDtm()
                : String.valueOf(System.currentTimeMillis());
        body.put("startDtm", startTimestamp);

        // Answers 변환 ("1=2 2=4 3=1 ..." 형식)
        String answerString = dto.getAnswers().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(" "));
        body.put("answers", answerString);

        // API 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(reportUrl, body, Map.class);
        Map<String, Object> result = response.getBody();

        // 응답 성공 여부 체크
        if (result == null || !"Y".equals(result.get("SUCC_YN"))) {
            String errorReason = (String) result.getOrDefault("ERROR_REASON", "알 수 없는 오류");
            throw new RuntimeException("외부 진단검사 실패: " + errorReason);
        }

        // RESULT 추출
        Map<String, Object> resultData = (Map<String, Object>) result.get("RESULT");
        String inspectSeq = String.valueOf(resultData.get("inspectSeq"));
        String resultUrl = String.valueOf(resultData.get("url"));

        // 검사 정보 & 학생 조회
        ExternalDiagnosticTest test = testRepository.findByQuestionApiCode(dto.getQestrnSeq())
                .orElseThrow(() -> new IllegalArgumentException("외부 심리검사 정보를 찾을 수 없습니다."));

        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        // 결과 저장
        ExternalDiagnosticResult saved = ExternalDiagnosticResult.builder()
                .test(test)
                .student(student)
                .inspectCode(inspectSeq)
                .resultUrl(resultUrl)
                .submittedAt(LocalDateTime.now())
                .build();
        resultRepository.save(saved);

        // DTO 반환
        return ExternalDiagnosisResultDto.builder()
                .inspectSeq(inspectSeq)
                .resultUrl(resultUrl)
                .build();
    }
}