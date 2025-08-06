package com.secondprojinitiumback.user.coreCompetency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseBulkRequestDto {
    private Long assessmentId;
    private List<UserResponseRequestDto> responses;
}
