package com.secondprojinitiumback.user.diagnostic.dto;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticResult;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosisResultDto {
    private String inspectSeq;
    private String resultUrl;
    private String testName;

    public static ExternalDiagnosisResultDto from(ExternalDiagnosticResult result) {
        return ExternalDiagnosisResultDto.builder()
                .inspectSeq(result.getInspectCode())
                .resultUrl(result.getResultUrl())
                .testName(result.getTest().getName())
                .build();
    }
}
