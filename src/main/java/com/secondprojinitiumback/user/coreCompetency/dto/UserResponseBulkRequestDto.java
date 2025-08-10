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

    // 사용자가 응답한 핵심역량 평가의 ID
    private Long assessmentId;

    // 사용자의 개별 문항 응답 목록
    private List<UserResponseRequestDto> responses;
}
