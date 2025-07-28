package com.secondprojinitiumback.user.diagnostic.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreLevelDto {
    private String levelName;
    private String description;
}