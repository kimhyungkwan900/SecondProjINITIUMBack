package com.secondprojinitiumback.user.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DscsnApplyResponseDto extends DscsnApplyBaseDto{

    private StudentDto studentDto;

    private DscsnScheduleResponseDto dscsnScheduleDto;

    private DscsnKindDto dscsnKindDto;
}
