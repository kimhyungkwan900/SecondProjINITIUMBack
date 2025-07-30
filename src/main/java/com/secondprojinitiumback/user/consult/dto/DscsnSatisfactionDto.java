package com.secondprojinitiumback.user.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnSatisfactionDto {

    private String dscsnSatisfyScore; //만족도 지표

    private String dscsnImp; //개선점

    private String dscsnInfoId;  //신청서 ID
}
