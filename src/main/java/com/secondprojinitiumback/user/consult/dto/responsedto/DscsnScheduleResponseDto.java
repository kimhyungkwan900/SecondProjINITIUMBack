package com.secondprojinitiumback.user.consult.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnScheduleResponseDto {

    //교원 정보 중 필요한 정보만 필드로 생성
    private String dscsnDtId; // 상담일정 ID

    private String empNo; // 상담사, 교수 사번

    private String empName; // 상담사, 교수 이름

    private String schoolSubject; // 상담사, 교수 소속

    private String scheduleDate; // 상담일정 날짜 (YYYYMMdd 형식)

    private String startTime; // 상담일정 시간 (HHMM 형식)

    private String dscsnYn; // 예약 여부 (Y/N)
}
