package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosisSubmitRequestDto {
    private Long userId;
    private Long testId;
    private List<AnswerSubmission> answers;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class AnswerSubmission {
        private Long questionId;
        private Integer selectedValue;
    }
}
