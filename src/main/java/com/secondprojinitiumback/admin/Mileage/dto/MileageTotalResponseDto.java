package com.secondprojinitiumback.admin.Mileage.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageTotalResponseDto {

    private String studentNo;  // 학번
    private Double totalScore;    // 누적 마일리지 점수

}
