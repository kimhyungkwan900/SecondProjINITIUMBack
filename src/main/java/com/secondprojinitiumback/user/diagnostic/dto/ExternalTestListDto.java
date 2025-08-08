package com.secondprojinitiumback.user.diagnostic.dto;

import com.secondprojinitiumback.user.diagnostic.domain.ExternalDiagnosticTest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ExternalTestListDto {
    private Long id;
    private String name;            // 검사명
    private String provider;        // 제공 기관
    private String questionApiCode; // API 코드(qestrnSeq)
    private String targetCode;      // 대상 코드

    public static ExternalTestListDto from(ExternalDiagnosticTest test) {
        return ExternalTestListDto.builder()
                .id(test.getId())
                .name(test.getName())
                .provider(test.getProvider())
                .questionApiCode(test.getQuestionApiCode())
                .targetCode(test.getTargetCode())
                .build();
    }
}
