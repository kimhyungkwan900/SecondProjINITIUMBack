package com.secondprojinitiumback.user.consult.dto.responsedto;

import com.secondprojinitiumback.user.consult.dto.DscsnKindDto;
import com.secondprojinitiumback.user.consult.dto.StudentDto;
import com.secondprojinitiumback.user.consult.dto.basedto.DscsnApplyBaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DscsnApplyResponseDto extends DscsnApplyBaseDto {

    private StudentDto studentDto;

    private DscsnScheduleResponseDto dscsnScheduleDto;

    private DscsnKindDto dscsnKindDto;
}
