package com.secondprojinitiumback.user.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnScheduleDto {

    private String empNo; // 상담사, 교수 사번

    private String scheduleDate; // 상담일정 날짜 (YYYYMMdd 형식)

    private String startTime; // 상담일정 시간 (HHMM 형식)

    private String dscsnYn; // 예약 여부 (Y/N)
}