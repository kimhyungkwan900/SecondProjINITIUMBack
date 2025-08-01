package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;

import java.time.LocalDateTime;

public class ExtracurricularAttendanceFormDTO {
    private Long atndcId; // 출석 ID
    private ExtracurricularSchedule extracurricularSchedule;
    private StdntInfo stdntInfo; // 학생 정보
    private LocalDateTime atndcDt; // 출석 일시
    private String atndcYn; // 출석 상태 (예: 출석, 지각, 결석 등)
}
