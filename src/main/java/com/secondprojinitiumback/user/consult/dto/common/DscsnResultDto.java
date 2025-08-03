package com.secondprojinitiumback.user.consult.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnResultDto {

    String dscsnInfoId;

    String releaseYn;

    String result;
}