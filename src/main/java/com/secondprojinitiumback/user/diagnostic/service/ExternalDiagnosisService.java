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
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    private String questionUrl; // ex) https://www.career.go.kr/inspct/openapi/test/questions

    @Value("${career.api.report-url}")
    private String reportUrl;   // ex) https://www.career.go.kr/inspct/openapi/test/report

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
                .map(ExternalDiagnosisResultDto::from)
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
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uri.toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // 필요하면 3xx 수동 처리 (대부분 자동처리됨)
        if (response.getStatusCode().is3xxRedirection() && response.getHeaders().getLocation() != null) {
            String redirectUrl = response.getHeaders().getLocation().toString();
            log.warn("Redirect detected ({}): {}", response.getStatusCode(), redirectUrl);
            response = restTemplate.exchange(redirectUrl, HttpMethod.GET, entity, String.class);
        }

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("CareerNet API 호출 실패 - 상태코드: " + response.getStatusCode());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException("CareerNet API 응답 파싱 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 📄 외부 진단 문항 조회 (파싱된 응답 DTO: 보기 텍스트 + 실제 전송값 포함)
     */
    public ExternalQuestionResponseDto getParsedExternalQuestions(String qestrnSeq) {
        Map<String, Object> raw = fetchExternalQuestions(qestrnSeq);

        // CareerNet v1 응답의 상단 메타(없을 수 있어 기본값 처리)
        String title = Optional.ofNullable((String) raw.get("qestrnNm")).orElse("제목 없음");
        String description = Optional.ofNullable((String) raw.get("qestrnDesc")).orElse("");

        // RESULT 배열
        List<Map<String, Object>> resultList = Optional.ofNullable((List<Map<String, Object>>) raw.get("RESULT"))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> {
                    log.error("CareerNet API 응답에 RESULT 데이터가 없음. qestrnSeq={}, raw={}", qestrnSeq, raw);
                    return new RuntimeException("CareerNet API에서 질문 데이터를 가져오지 못했습니다.");
                });

        // 각 문항 파싱 (answerNN + answerScoreNN)
        List<ExternalQuestionResponseDto.QuestionItem> questions = resultList.stream()
                .map(item -> {
                    List<ExternalQuestionResponseDto.Option> options = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        String idx = String.format("%02d", i);
                        String text = (String) item.get("answer" + idx);
                        String val  = (String) item.get("answerScore" + idx);
                        if (text != null && !text.isBlank() && val != null && !val.isBlank()) {
                            options.add(ExternalQuestionResponseDto.Option.builder()
                                    .text(text)
                                    .value(val)
                                    .build());
                        }
                    }
                    return ExternalQuestionResponseDto.QuestionItem.builder()
                            .questionText(Optional.ofNullable((String) item.get("question")).orElse("질문 없음"))
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
     * ✅ 외부 진단 검사 결과 제출 및 저장 (CareerNet V1)
     */
    public ExternalDiagnosisResultDto submitExternalResult(ExternalDiagnosisRequestDto dto) {
        try {
            // 1) startDtm 보정
            String startDtm = (dto.getStartDtm() == null || dto.getStartDtm().isBlank())
                    ? String.valueOf(System.currentTimeMillis())
                    : dto.getStartDtm();

            // 2) JSON 페이로드 구성 (!!! apikey 소문자 주의)
            Map<String, Object> payload = new HashMap<>();
            payload.put("apikey", apiKey);
            payload.put("qestrnSeq", dto.getQestrnSeq());
            payload.put("trgetSe", dto.getTrgetSe());
            payload.put("name", dto.getName());
            payload.put("gender", dto.getGender());
            payload.put("school", Optional.ofNullable(dto.getSchool()).orElse(""));
            payload.put("grade", dto.getGrade());
            payload.put("email", Optional.ofNullable(dto.getEmail()).orElse(""));
            payload.put("startDtm", Long.parseLong(startDtm)); // 숫자로 보내는 편이 안전
            payload.put("answers", dto.getAnswers());          // "B1=5 B2=3 ..." 또는 "2,2,3,..."

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // 3) POST (JSON)
            ResponseEntity<Map> response = restTemplate.exchange(
                    reportUrl, HttpMethod.POST, request, Map.class
            );

            Map<String, Object> bodyMap = response.getBody();
            if (bodyMap == null || !bodyMap.containsKey("RESULT")) {
                throw new RuntimeException("검사 결과 파싱 실패: RESULT 키 없음");
            }

            Map<String, Object> resultMap = (Map<String, Object>) bodyMap.get("RESULT");
            String url = (String) resultMap.get("url");
            String inspctSeq = String.valueOf(resultMap.get("inspctSeq"));

            // 4) DB 저장
            Student student = studentRepository.findByStudentNo(dto.getStudentNo())
                    .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다: " + dto.getStudentNo()));

            ExternalDiagnosticTest test = testRepository.findByQuestionApiCode(dto.getQestrnSeq())
                    .orElseThrow(() -> new IllegalArgumentException("검사를 찾을 수 없습니다: qestrnSeq=" + dto.getQestrnSeq()));

            ExternalDiagnosticResult saved = resultRepository.save(
                    ExternalDiagnosticResult.builder()
                            .student(student)
                            .test(test)
                            .inspectCode(inspctSeq)
                            .resultUrl(url)
                            .submittedAt(LocalDateTime.now())
                            .build()
            );

            return ExternalDiagnosisResultDto.from(saved);

        } catch (HttpClientErrorException e) {
            log.error("CareerNet 응답 오류: status={} body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("외부 진단검사 요청 실패: " + e.getStatusCode(), e);
        } catch (Exception e) {
            log.error("외부 진단검사 처리 중 예외", e);
            throw new RuntimeException("외부 진단검사 처리 중 예외 발생", e);
        }
    }
}
