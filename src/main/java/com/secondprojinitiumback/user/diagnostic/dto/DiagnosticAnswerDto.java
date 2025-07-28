package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticAnswerDto {
    private Long id;
    private String content;
    private Integer score;
    private Integer selectValue;
}
