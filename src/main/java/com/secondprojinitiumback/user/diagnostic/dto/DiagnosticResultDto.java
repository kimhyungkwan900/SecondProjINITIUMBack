package com.secondprojinitiumback.user.diagnostic.dto;

import com.secondprojinitiumback.user.diagnostic.domain.DiagnosticResult;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResultDto {

    private Long resultId;
    private String studentNo;
    private Long testId;
    private String testName; // 🔹 추가
    private Integer totalScore;
    private LocalDateTime completionDate;
    private String interpretedMessage;

    public static DiagnosticResultDto from(DiagnosticResult result, String interpreted) {
        return DiagnosticResultDto.builder()
                .resultId(result.getId())
                .studentNo(result.getStudent().getStudentNo())
                .testId(result.getTest().getId())
                .testName(result.getTest().getName()) // 🔹 이름 포함
                .totalScore(result.getTotalScore())
                .completionDate(result.getCompletionDate())
                .interpretedMessage(interpreted)
                .build();
    }
}

