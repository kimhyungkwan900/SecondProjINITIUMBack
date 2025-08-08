package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalQuestionResponseDto {
    private String title;         // 검사명 또는 간단한 설명
    private String description;   // 검사 설명 (선택)
    private List<QuestionItem> questions;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class QuestionItem {
        private String questionText;     // 질문 내용
        private List<Option> options;    // 보기 목록(텍스트 + 실제 전송값)
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Option {
        private String text;   // answer01 ~ answer10
        private String value;  // answerScore01 ~ answerScore10 (실제 전송값)
    }
}
