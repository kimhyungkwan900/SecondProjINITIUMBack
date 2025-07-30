package com.secondprojinitiumback.admin.extracurricular.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "extracurricular_schedule")
public class ExtracurricularSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edu_shdl_id")
    private Long eduShdlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_mng_id")
    private ExtracurricularProgram extracurricularProgram; // 프로그램 관리 ID

    @Column(name = "edu_dt")
    private LocalDateTime eduDt; // 시작 날짜 시간

    @Column(name = "edu_edn_tm")
    private LocalTime eduEdnTm; // 종료 시간
}
