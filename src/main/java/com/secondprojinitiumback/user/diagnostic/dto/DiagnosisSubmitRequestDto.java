package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosisSubmitRequestDto {

    /**
     * 학생 식별자 (STDNT_NO)
     * 기존 userId(Long) → studentNo(String)으로 변경
     */
    private String studentNo;

    /**
     * 진단검사 ID
     */
    private Long testId;

    /**
     * 사용자가 선택한 답변 목록
     */
    private List<AnswerSubmission> answers;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AnswerSubmission {
        /**
         * 질문 ID
         */
        private Long questionId;

        /**
         * 사용자가 선택한 보기 값
         */
        private Integer selectedValue;
    }
}
