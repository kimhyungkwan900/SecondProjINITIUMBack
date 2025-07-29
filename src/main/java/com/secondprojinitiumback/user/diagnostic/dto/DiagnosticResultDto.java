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

    /**
     * 학생 학번 (STDNT_NO)
     * 기존 userId(Long) → studentNo(String)으로 변경
     */
    private String studentNo;

    private Long testId;
    private Integer totalScore;
    private LocalDateTime completionDate;

    /**
     * 점수 해석 메시지
     */
    private String interpretedMessage;
}
