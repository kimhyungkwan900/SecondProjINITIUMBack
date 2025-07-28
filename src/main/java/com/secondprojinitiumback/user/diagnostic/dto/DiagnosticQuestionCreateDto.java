package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DiagnosticQuestionCreateDto {
    private String content;
    private Integer order;
    private String answerType;
    private List<DiagnosticAnswerCreateDto> answers;
}
