package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class ExtracurricularSurveyDTO {
    private Long srvyId;
    private Long eduMngId;
    private String srvyTtl; // 설문 제목
    private String srvyQitemCn; // 설문 문항 내용
    private LocalDate srvyBgngDt; // 설문 시작 일시
    private LocalDate srvyEndDt; // 설문 종료 일시
}
