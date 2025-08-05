package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.Builder;
import lombok.Data;

// DiagnosticResultDetailDto.java
@Data
@Builder
public class DiagnosticResultDetailDto {
    private Long questionId;
    private String questionContent;
    private Integer selectedValue;
    private Integer score;
}
