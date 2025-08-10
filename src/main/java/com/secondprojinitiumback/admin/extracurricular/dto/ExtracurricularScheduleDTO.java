package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class ExtracurricularScheduleDTO {
    private Long eduShdlId; // 일정 ID
    private ExtracurricularProgram extracurricularProgram;
    private LocalDateTime eduDt; // 일정 날짜 시간
    private LocalTime eduEndTm; // 종료 시간
}
