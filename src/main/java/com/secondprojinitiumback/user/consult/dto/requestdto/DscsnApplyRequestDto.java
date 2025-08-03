package com.secondprojinitiumback.user.consult.dto.requestdto;

import com.secondprojinitiumback.user.consult.dto.basedto.DscsnApplyBaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DscsnApplyRequestDto extends DscsnApplyBaseDto {

    private String studentNo;

    private String dscsnDtId;

    private String dscsnKindId;
}
