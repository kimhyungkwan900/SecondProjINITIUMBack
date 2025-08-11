package com.secondprojinitiumback.admin.coreCompetency.dto;

import com.secondprojinitiumback.admin.coreCompetency.domain.ResponseChoiceOption;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 보기(객관식 선택지) 정보 DTO
public class ResponseChoiceOptionDto {
    private Long optionId;           // 선택지 ID
    private String label;            // 보기 내용
    private Integer score;           // 배점
    private Integer optionNo;        // 보기 번호 (1부터 시작)
    private String answerType; // 읽기전용: 항상 "SINGLE"

    public static ResponseChoiceOptionDto fromEntity(ResponseChoiceOption option) {
        return ResponseChoiceOptionDto.builder()
                .optionId(option.getId())
                .label(option.getLabel())
                .score(option.getScore())
                .optionNo(option.getOptionNo())
                .answerType(option.getAnswerType())
                .build();
    }
}
