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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
     * ✅ 외부 진단 검사 결과 제출 및 저장 (CareerNet V1 기준, 검사자 타입 무조건 대학생 적용)
     */
    public ExternalDiagnosisResultDto submitExternalResult(ExternalDiagnosisRequestDto dto) {

        // 🔍 학생 정보 조회
        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        // 🔄 성별 코드 매핑 (팀 코드 → CareerNet 코드)
        String careerNetGender;
        if ("10".equals(dto.getGender())) {
            careerNetGender = "100323"; // 남자
        } else if ("20".equals(dto.getGender())) {
            careerNetGender = "100324"; // 여자
        } else {
            throw new IllegalArgumentException("지원되지 않는 성별 코드입니다: " + dto.getGender());
        }

        // 🔄 검사자 타입 무조건 대학생
        String careerNetTrgetSe = "100208";

        // 📌 CareerNet V1 API는 application/x-www-form-urlencoded 방식으로 전송
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Accept", "application/json");
        headers.set("User-Agent", "Mozilla/5.0");

        // 📌 Form Data 생성
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("apikey", apiKey.trim());
        formData.add("qestrnSeq", dto.getQestrnSeq());
        formData.add("trgetSe", careerNetTrgetSe); // 🔹 항상 대학생 코드
        formData.add("name", student.getName());
        formData.add("gender", careerNetGender);   // 🔹 매핑된 성별 코드
        formData.add("school", dto.getSchool() != null ? dto.getSchool() : "");
        formData.add("grade", dto.getGrade());
        formData.add("startDtm", dto.getStartDtm() != null ? dto.getStartDtm() : String.valueOf(System.currentTimeMillis()));
        formData.add("answers", dto.getAnswers()); // 🔹 "1=5 2=3 ..." 형식 그대로

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        log.info("CareerNet Report API Request Body: {}", formData);

        try {
            // 📌 API 요청
            ResponseEntity<String> response = restTemplate.exchange(
                    reportUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 🔍 응답 로깅
            log.info("CareerNet Report API Status: {}", response.getStatusCode());
            log.info("CareerNet Report API Headers: {}", response.getHeaders());
            log.info("CareerNet Report API Raw Body: {}", response.getBody());

            // 📌 상태 코드 체크
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("CareerNet API 호출 실패 - 상태코드: " + response.getStatusCode());
            }

            // 📌 응답 Body JSON → Map 변환
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> result = mapper.readValue(response.getBody(), Map.class);

            // 📌 성공 여부 체크
            if (result == null || !"Y".equalsIgnoreCase((String) result.get("SUCC_YN"))) {
                throw new RuntimeException("외부 진단검사 실패: " +
                        (result != null ? result.getOrDefault("ERROR_REASON", "알 수 없는 오류") : "응답 없음"));
            }

            // 📌 RESULT 데이터 파싱
            Map<String, Object> resultData = (Map<String, Object>) result.get("RESULT");
            if (resultData == null) {
                throw new RuntimeException("CareerNet API 응답에 RESULT 데이터가 없습니다.");
            }

            String inspectSeq = String.valueOf(resultData.getOrDefault("inspctSeq", resultData.get("inspect_seq")));
            String resultUrl = String.valueOf(resultData.get("url"));

            // 📌 DB 저장
            ExternalDiagnosticTest test = testRepository.findByQuestionApiCode(dto.getQestrnSeq())
                    .orElseThrow(() -> new IllegalArgumentException("외부 심리검사 정보를 찾을 수 없습니다."));

            ExternalDiagnosticResult saved = ExternalDiagnosticResult.builder()
                    .test(test)
                    .student(student)
                    .inspectCode(inspectSeq)
                    .resultUrl(resultUrl)
                    .submittedAt(LocalDateTime.now())
                    .build();
            resultRepository.save(saved);

            return ExternalDiagnosisResultDto.builder()
                    .inspectSeq(inspectSeq)
                    .resultUrl(resultUrl)
                    .testName(test.getName())
                    .build();

        } catch (Exception e) {
            log.error("외부 진단검사 요청 중 오류 발생", e);
            throw new RuntimeException("외부 진단검사 요청 실패: " + e.getMessage(), e);
        }
    }

}