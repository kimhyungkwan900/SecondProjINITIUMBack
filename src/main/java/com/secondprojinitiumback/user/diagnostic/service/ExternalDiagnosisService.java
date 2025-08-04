package com.secondprojinitiumback.user.diagnostic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
     * 🔍 외부 진단검사 전체 목록 조회
     */
    public List<ExternalTestListDto> getAvailableExternalTests() {
        return testRepository.findAll().stream()
                .map(ExternalTestListDto::from)
                .toList();
    }

    /**
     * 📜 특정 학생의 모든 외부 검사 결과 조회
     */
    public List<ExternalDiagnosisResultDto> getAllResultsByStudent(String studentNo) {
        return resultRepository.findByStudent_StudentNo(studentNo).stream()
                .map(result -> ExternalDiagnosisResultDto.builder()
                        .inspectSeq(result.getInspectCode())
                        .resultUrl(result.getResultUrl())
                        .testName(result.getTest().getName())
                        .build())
                .toList();
    }

    /**
     * 🔍 외부 진단검사 검색
     */
    public List<ExternalTestListDto> searchExternalTestsByName(String keyword) {
        return testRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(ExternalTestListDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 🔍 페이징 처리된 외부 진단검사 검색
     */
    public Page<ExternalTestListDto> getPagedExternalTests(String keyword, Pageable pageable) {
        Page<ExternalDiagnosticTest> page = testRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return page.map(ExternalTestListDto::from);
    }

    /**
     * 📄 외부 진단 문항 조회 (원본 응답 Map)
     */
    public Map<String, Object> fetchExternalQuestions(String qestrnSeq) {
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(questionUrl)
                .queryParam("apikey", apiKey)
                .queryParam("q", qestrnSeq)
                .build(true);

        log.info("CareerNet API Request URL: {}", uri.toUriString());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("User-Agent", "Mozilla/5.0");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // 첫 요청
        ResponseEntity<String> response = restTemplate.exchange(
                uri.toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // 🔄 3xx 리다이렉트 수동 처리
        if (response.getStatusCode().is3xxRedirection()) {
            String redirectUrl = response.getHeaders().getLocation().toString();
            log.warn("Redirect detected ({}): {}", response.getStatusCode(), redirectUrl);

            response = restTemplate.exchange(
                    redirectUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("CareerNet API 호출 실패 - 상태코드: " + response.getStatusCode());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("CareerNet API 응답 파싱 실패: " + e.getMessage());
        }
    }





    /**
     * 📄 외부 진단 문항 조회 (파싱된 응답 DTO)
     */
    public ExternalQuestionResponseDto getParsedExternalQuestions(String qestrnSeq) {
        Map<String, Object> raw = fetchExternalQuestions(qestrnSeq);

        // 🔹 CareerNet API에서 검사 이름 읽기 (qestrnNm → qestrnTitle 대체)
        String title = Optional.ofNullable((String) raw.get("qestrnNm"))
                .orElse("제목 없음");

        // 🔹 CareerNet API에서 검사 설명 읽기
        String description = Optional.ofNullable((String) raw.get("qestrnDesc"))
                .orElse("");

        // ✅ RESULT 배열 가져오기
        List<Map<String, Object>> resultList = Optional.ofNullable((List<Map<String, Object>>) raw.get("RESULT"))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> {
                    log.error("CareerNet API 응답에 RESULT 데이터가 없음. qestrnSeq={}, raw={}", qestrnSeq, raw);
                    return new RuntimeException("CareerNet API에서 질문 데이터를 가져오지 못했습니다.");
                });

        // ✅ 각 질문/보기 데이터 변환
        List<ExternalQuestionResponseDto.QuestionItem> questions = resultList.stream()
                .map(item -> {
                    List<String> options = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        String answer = (String) item.get("answer" + String.format("%02d", i));
                        if (answer != null && !answer.isBlank()) {
                            options.add(answer);
                        }
                    }

                    return ExternalQuestionResponseDto.QuestionItem.builder()
                            .questionText(Optional.ofNullable((String) item.get("question"))
                                    .orElse("질문 없음"))
                            .options(options)
                            .build();
                })
                .toList();

        return ExternalQuestionResponseDto.builder()
                .title(title)
                .description(description)
                .questions(questions)
                .build();
    }


    /**
     * ✅ 외부 진단 검사 결과 제출 및 저장 (V1 전용)
     */
    public ExternalDiagnosisResultDto submitExternalResult(ExternalDiagnosisRequestDto dto) {

        // 학생 정보 조회
        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        // Request Body 생성 (V1 형식)
        Map<String, Object> body = new HashMap<>();
        body.put("apikey", apiKey.trim());
        body.put("qestrnSeq", dto.getQestrnSeq());
        body.put("trgetSe", dto.getTrgetSe());
        body.put("name", student.getName());
        body.put("gender", dto.getGender());
        body.put("school", dto.getSchool() != null ? dto.getSchool() : "");
        body.put("grade", dto.getGrade());
        body.put("startDtm", dto.getStartDtm() != null ? dto.getStartDtm() : String.valueOf(System.currentTimeMillis()));
        body.put("answers", dto.getAnswers()); // 🔹 V1은 String 형식 "1=5 2=3 ..."

        log.info("CareerNet Report API Request: {}", body); // 🔍 요청 바디 로그

        // API 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(reportUrl, body, Map.class);
        Map<String, Object> result = response.getBody();

        log.info("CareerNet Report API Response: {}", result); // 🔍 응답 로그

        // 응답 성공 여부 체크
        if (result == null || !"Y".equals(result.get("SUCC_YN"))) {
            String errorReason = result != null ? (String) result.getOrDefault("ERROR_REASON", "알 수 없는 오류") : "응답 없음";
            throw new RuntimeException("외부 진단검사 실패: " + errorReason);
        }

        // RESULT 추출
        Map<String, Object> resultData = (Map<String, Object>) result.get("RESULT");
        String inspectSeq = String.valueOf(resultData.get("inspctSeq"));
        String resultUrl = String.valueOf(resultData.get("url"));

        // 검사 정보 조회
        ExternalDiagnosticTest test = testRepository.findByQuestionApiCode(dto.getQestrnSeq())
                .orElseThrow(() -> new IllegalArgumentException("외부 심리검사 정보를 찾을 수 없습니다."));

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
                .testName(test.getName())
                .build();
    }
}