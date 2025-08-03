package com.secondprojinitiumback.user.consult.dto.common;

import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnScheduleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
public class DscsnScheduleListDto {
    private Page<DscsnScheduleResponseDto> dscsnSchedules;
    private Integer maxPage;
    private Integer totalPage;
}
