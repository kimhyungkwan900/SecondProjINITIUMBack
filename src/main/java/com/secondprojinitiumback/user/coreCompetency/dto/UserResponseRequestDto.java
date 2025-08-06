package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseRequestDto {
    private Long questionId;
    private String label;
    private Integer score;
}
