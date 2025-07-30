package com.secondprojinitiumback.user.diagnostic.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResultDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    public byte[] generateDiagnosisResultPdf(DiagnosticResult result,
                                             List<DiagnosticResultDetail> details,
                                             String interpretation) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // ✅ 한글 폰트 설정 (운영 환경 포함 안정적 로드)
        File fontFile = ResourceUtils.getFile("classpath:fonts/NotoSansKR-Regular.ttf");
        PdfFont koreanFont = PdfFontFactory.createFont(fontFile.getAbsolutePath(), PdfEncodings.IDENTITY_H, true);
        document.setFont(koreanFont);

        // ✅ 제목
        document.add(new Paragraph("진단검사 결과 요약")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // ✅ 기본 정보 (Student 연동 적용)
        document.add(new Paragraph("검사명: " + result.getTest().getName()));
        document.add(new Paragraph("학생번호: " + result.getStudent().getStudentNo()));
        document.add(new Paragraph("학생명: " + result.getStudent().getName()));
        document.add(new Paragraph("총점: " + result.getTotalScore()));
        document.add(new Paragraph("해석: " + interpretation));
        document.add(new Paragraph("응답일: " + result.getCompletionDate()));
        document.add(new Paragraph(" "));

        // ✅ 상세 문항별 결과
        document.add(new Paragraph("문항별 응답 결과")
                .setBold()
                .setFontSize(14)
                .setMarginTop(10));

        int index = 1;
        for (DiagnosticResultDetail detail : details) {
            document.add(new Paragraph(index++ + ". " + detail.getQuestion().getContent())
                    .setFontSize(11));
            document.add(new Paragraph("   선택값: " + detail.getSelectedValue() +
                    ", 점수: " + detail.getScore()));
        }

        document.close();
        return out.toByteArray();
    }
}
