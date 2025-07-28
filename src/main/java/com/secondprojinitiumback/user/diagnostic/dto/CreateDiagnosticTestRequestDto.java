package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDiagnosticTestRequestDto {
    private String name;
    private String description;
    private Boolean useYn;
    private List<DiagnosticQuestionCreateDto> questions;
}