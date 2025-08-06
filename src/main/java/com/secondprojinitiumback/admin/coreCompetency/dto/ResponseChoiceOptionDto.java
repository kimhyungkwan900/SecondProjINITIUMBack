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

    public static ResponseChoiceOptionDto fromEntity(ResponseChoiceOption option) {
        return ResponseChoiceOptionDto.builder()
                .optionId(option.getId())
                .label(option.getLabel())
                .score(option.getScore())
                .build();
    }
}
