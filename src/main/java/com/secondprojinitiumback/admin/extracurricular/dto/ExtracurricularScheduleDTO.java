package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularScheduleDTO {
    private Long eduShdlId; // 일정 ID
    private Long eduMngId;// 프로그램 ID
    private LocalDateTime eduDt; // 일정 날짜 시간
    private LocalTime eduEndTm; // 종료 시간
}
