package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseRequestDto {

    // 응답한 문항의 ID
    private Long questionId;

    // 사용자가 선택한 보기의 라벨 (예: "매우 그렇다", "그렇다" 등)
    private String label;

    // 선택한 보기의 점수 값 (예: 5, 4, 3 등)
    private Integer score;
}
