package com.secondprojinitiumback.user.consult.dto;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnApplyResponseDto {

    private String dscsnApplyId;

    private String studentTelno;

    private String dscsnApplyCn;

    private String dscsnOnlineYn;

    private StudentDto studentDto;

    private DscsnScheduleDto dscsnScheduleDto;

    private DscsnKindDto dscsnKindDto;
}
