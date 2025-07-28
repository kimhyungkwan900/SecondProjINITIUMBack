package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosisResultDto {
    private String inspectSeq;  // 커리어넷 검사 결과 코드
    private String resultUrl;   // 검사 결과 페이지 URL
}