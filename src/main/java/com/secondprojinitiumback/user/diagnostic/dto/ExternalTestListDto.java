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
    private String name;              // ← testName → name
    private String provider;
    private String questionApiCode;

    public static ExternalTestListDto from(ExternalDiagnosticTest test) {
        return ExternalTestListDto.builder()
                .id(test.getId())
                .name(test.getName())
                .provider(test.getProvider())
                .questionApiCode(test.getQuestionApiCode())
                .build();
    }
}