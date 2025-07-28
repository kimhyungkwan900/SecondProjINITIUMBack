package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticQuestionDto {
    private Long id;
    private String content;
    private Integer order;
    private String answerType;
    private List<DiagnosticAnswerDto> answers;
}
