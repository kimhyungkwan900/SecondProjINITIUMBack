package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

import java.util.List;

/**
 * 사용자 핵심역량 진단 응답 제출용 DTO
 */
// DTO
import com.fasterxml.jackson.annotation.JsonProperty;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserCoreCompetencySubmitDto {
    private Long assessmentId;

    @JsonProperty("responses")   // 프론트 필드명과 매핑
    private List<ResponseItem> response;

    @Getter @Setter
    public static class ResponseItem {
        private Long questionId;
        private String label;
        private int score;
    }
}

