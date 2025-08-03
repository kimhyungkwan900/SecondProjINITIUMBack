package com.secondprojinitiumback.user.consult.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
public class DscsnKindListDto {
    private Page<DscsnKindDto> dscsnKinds;
    private Integer maxPage;
    private Integer totalPage;
}
