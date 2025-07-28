package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResultDetailDto {
    private Long questionId;
    private String questionContent;
    private Integer selectedValue;
    private Integer score;
}