package com.secondprojinitiumback.user.diagnostic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
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

    private final ExternalDiagnosticTestRepository testRepository;     // 외부 검사 정의 조회용
    private final ExternalDiagnosticResultRepository resultRepository; // 외부 검사 결과 저장/조회용
    private final StudentRepository studentRepository;                 // 학생 조회용
    private final RestTemplate restTemplate;                           // CareerNet API 호출용

    @Value("${career.api.key}")
    private String apiKey; // CareerNet API 인증키

    @Value("${career.api.questions-url}")
    private String questionUrl; // 문항 조회 API URL

    @Value("${career.api.report-url}")
    private String reportUrl;   // 결과 제출 API URL

    /**
     * 🔍 모든 외부 진단검사 목록 조회
     * - DB의 ExternalDiagnosticTest 전체를 조회 후 DTO로 변환
     */
    public List<ExternalTestListDto> getAvailableExternalTests() {
        return testRepository.findAll().stream()
                .map(ExternalTestListDto::from)
                .toList();
    }

    /**
     * 특정 학생의 모든 외부 검사 결과 조회
     * - studentNo로 필터링
     */
    public List<ExternalDiagnosisResultDto> getAllResultsByStudent(String studentNo) {
        return resultRepository.findByStudent_StudentNo(studentNo).stream()
                .map(ExternalDiagnosisResultDto::from)
                .toList();
    }

    /**
     * 외부 검사명 기반 검색
     * - 대소문자 무시, 부분 일치
     */
    public List<ExternalTestListDto> searchExternalTestsByName(String keyword) {
        return testRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(ExternalTestListDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 외부 진단검사 페이징 검색
     * - Pageable 파라미터로 페이지/정렬 가능
     */
    public Page<ExternalTestListDto> getPagedExternalTests(String keyword, Pageable pageable) {
        Page<ExternalDiagnosticTest> page = testRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return page.map(ExternalTestListDto::from);
    }

    /**
     * 외부 문항 원본 조회
     * - CareerNet API GET 호출
     * - Map<String,Object> 그대로 반환
     */
    public Map<String, Object> fetchExternalQuestions(String qestrnSeq) {
        // 요청 URL 구성
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(questionUrl)
                .queryParam("apikey", apiKey)
                .queryParam("q", qestrnSeq)
                .build(true);

        // HTTP 헤더 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0");// 일부 API는 User-Agent 필수

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // API 호출
        ResponseEntity<String> response = restTemplate.exchange(
                uri.toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // 리다이렉션 처리
        if (response.getStatusCode().is3xxRedirection() && response.getHeaders().getLocation() != null) {
            String redirectUrl = response.getHeaders().getLocation().toString();
            log.warn("Redirect detected ({}): {}", response.getStatusCode(), redirectUrl);
            response = restTemplate.exchange(redirectUrl, HttpMethod.GET, entity, String.class);
        }

        // 응답 코드 검증
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(ErrorCode.EXTERNAL_DIAGNOSIS_API_ERROR);
        }

        // JSON → Map 변환
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), Map.class);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EXTERNAL_DIAGNOSIS_API_ERROR);
        }
    }

    /**
     * 외부 문항 파싱 조회
     * - 보기(text/value) 포함된 DTO 반환
     */
    public ExternalQuestionResponseDto getParsedExternalQuestions(String qestrnSeq) {
        Map<String, Object> raw = fetchExternalQuestions(qestrnSeq);

        // 상단 메타 정보
        String title = Optional.ofNullable((String) raw.get("qestrnNm")).orElse("제목 없음");
        String description = Optional.ofNullable((String) raw.get("qestrnDesc")).orElse("");

        // RESULT 배열 가져오기
        List<Map<String, Object>> resultList = Optional.ofNullable((List<Map<String, Object>>) raw.get("RESULT"))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new CustomException(ErrorCode.EXTERNAL_DIAGNOSIS_API_ERROR));

        // 문항 파싱
        List<ExternalQuestionResponseDto.QuestionItem> questions = resultList.stream()
                .map(item -> {
                    List<ExternalQuestionResponseDto.Option> options = new ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                        String idx = String.format("%02d", i);
                        String text = (String) item.get("answer" + idx);
                        String val  = (String) item.get("answerScore" + idx);
                        if (text != null && !text.isBlank() && val != null && !val.isBlank()) {
                            options.add(ExternalQuestionResponseDto.Option.builder()
                                    .text(text)   // 보기 내용
                                    .value(val)   // CareerNet 전송 값
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
     * ✅ 외부 검사 결과 제출 및 저장
     * - CareerNet API POST 요청
     * - 결과 URL + 검사코드 DB 저장
     */
    public ExternalDiagnosisResultDto submitExternalResult(ExternalDiagnosisRequestDto dto) {
        try {
            // 1) startDtm 기본값 처리
            String startDtm = (dto.getStartDtm() == null || dto.getStartDtm().isBlank())
                    ? String.valueOf(System.currentTimeMillis())
                    : dto.getStartDtm();

            // 2) JSON 요청 페이로드 생성
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

            // 3) HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            // 4) POST 요청
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<Map> response = restTemplate.exchange(reportUrl, HttpMethod.POST, request, Map.class);

            Map<String, Object> bodyMap = response.getBody();
            if (bodyMap == null || !bodyMap.containsKey("RESULT")) {
                throw new CustomException(ErrorCode.EXTERNAL_DIAGNOSIS_API_ERROR);
            }

            // 5) CareerNet 응답에서 결과 URL, 검사 코드 추출
            Map<String, Object> resultMap = (Map<String, Object>) bodyMap.get("RESULT");
            String url = (String) resultMap.get("url");
            String inspctSeq = String.valueOf(resultMap.get("inspctSeq"));

            // 6) DB 저장
            Student student = studentRepository.findByStudentNo(dto.getStudentNo())
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));

            ExternalDiagnosticTest test = testRepository.findByQuestionApiCode(dto.getQestrnSeq())
                    .orElseThrow(() -> new CustomException(ErrorCode.DIAGNOSTIC_TEST_NOT_FOUND));

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
