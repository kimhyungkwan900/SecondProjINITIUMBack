package com.secondprojinitiumback.user.consult.dto.common;

import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
public class DscsnInfoListDto {
    private Page<DscsnInfoResponseDto> dscsnInfos;

}
