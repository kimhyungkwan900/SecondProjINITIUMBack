package com.secondprojinitiumback.user.CoreCompetency.dto;

import java.util.List;

// 문항 + 보기 함께 조회하는 DTO
public class CoreCompetencyQuestionWithOptionsDto {
    private Long id;                  // 문항 ID
    private String questionName;      // 문항 내용
    private Integer questionNo;       // 문항 번호
    private Integer displayOrder;     // 출력 순서
    private Integer answerAllowCount; // 허용 응답 개수
    private Boolean isCommon;         // 공통문항 여부

    private List<ResponseChoiceOptionDto> options; // 보기 리스트

    public static class ResponseChoiceOptionDto {
        private Long optionId;        // 보기 ID
        private String label;         // 보기 라벨
        private Integer score;        // 보기 점수
    }
}

