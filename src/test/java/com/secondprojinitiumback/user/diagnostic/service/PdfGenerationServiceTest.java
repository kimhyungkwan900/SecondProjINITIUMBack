package com.secondprojinitiumback.user.diagnostic.service;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticTest;
import com.secondprojinitiumback.user.student.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PdfGenerationServiceTest {
    private PdfGenerationService pdfGenerationService;

    @BeforeEach
    void setUp() {
        pdfGenerationService = new PdfGenerationService();
    }

    @Test
    void generateDiagnosisResultPdf_성공() throws IOException {
        // given (테스트용 데이터)
        DiagnosticTest test = DiagnosticTest.builder()
                .id(1L)
                .name("강박증 진단검사")
                .build();

        Student student = Student.builder()
                .studentNo("20250001")
                .name("홍길동")
                .build();

        DiagnosticResult result = DiagnosticResult.builder()
                .id(100L)
                .test(test)
                .student(student)
                .totalScore(85)
                .completionDate(LocalDateTime.now())
                .build();

        DiagnosticResultDetail detail1 = DiagnosticResultDetail.builder()
                .question(new com.secondprojinitiumback.user.diagnostic.domain.DiagnosticQuestion(null, test, "Q1 내용", 1, null, null))
                .selectedValue(1)
                .score(5)
                .build();

        DiagnosticResultDetail detail2 = DiagnosticResultDetail.builder()
                .question(new com.secondprojinitiumback.user.diagnostic.domain.DiagnosticQuestion(null, test, "Q2 내용", 2, null, null))
                .selectedValue(2)
                .score(3)
                .build();

        List<DiagnosticResultDetail> details = List.of(detail1, detail2);

        String interpretation = "상 - 매우 양호";

        // when
        byte[] pdfBytes = pdfGenerationService.generateDiagnosisResultPdf(result, details, interpretation);

        // then
        assertThat(pdfBytes).isNotEmpty();

        // 🔹 실제 PDF 파일로 저장 (테스트 시 확인용, 배포환경에서는 불필요)
        try (FileOutputStream fos = new FileOutputStream("test-result.pdf")) {
            fos.write(pdfBytes);
        }
    }
}