package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResultDto {
    private Long resultId;
    private Long userId;
    private Long testId;
    private Integer totalScore;
    private LocalDateTime completionDate;
    private String interpretedMessage;
}