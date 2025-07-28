package com.secondprojinitiumback.user.diagnostic.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    public byte[] generateDiagnosisResultPdf(DiagnosticResult result, List<DiagnosticResultDetail> details, String interpretation) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        String fontPath = "fonts/NotoSansKR-Regular.ttf";

        PdfFont koreanFont = PdfFontFactory.createFont(
                getClass().getClassLoader().getResource(fontPath).getPath(),
                PdfEncodings.IDENTITY_H, true
        );
        document.setFont(koreanFont); // 전체 문서에 적용

        document.add(new Paragraph("진단검사 결과 요약")
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        document.add(new Paragraph("검사명: " + result.getTest().getName()));
        document.add(new Paragraph("사용자 ID: " + result.getUserId()));
        document.add(new Paragraph("총점: " + result.getTotalScore()));
        document.add(new Paragraph("해석: " + interpretation));
        document.add(new Paragraph("응답일: " + result.getCompletionDate()));
        document.add(new Paragraph(" "));

        // 상세 문항별 결과
        document.add(new Paragraph("문항별 응답 결과").setBold().setFontSize(14).setMarginTop(10));

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