package com.secondprojinitiumback.user.consult.dto.basedto;

import lombok.AllArgsConstructor;
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

}
