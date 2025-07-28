package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosticAnswerCreateDto {
    private String content;
    private Integer score;
    private Integer selectValue;
}