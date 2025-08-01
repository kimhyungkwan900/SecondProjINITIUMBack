package com.secondprojinitiumback.user.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DscsnApplyBaseDto {

    private String dscsnApplyId;

    private String studentTelno;

    private String dscsnApplyCn;

    private String dscsnOnlineYn;

}
