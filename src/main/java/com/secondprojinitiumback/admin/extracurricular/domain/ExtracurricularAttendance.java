package com.secondprojinitiumback.admin.extracurricular.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "extracurricular_attendance")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularAttendance {
    @Id
    @Column(name = "atndc_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atndcId; // 출석 ID

    @ManyToOne
    @JoinColumn(name = "edu_shdl_id")
    private ExtracurricularSchedule extracurricularSchedule; // 비교과 일정 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "")
    private Student student;
    @Column(name = "atndc_dt")
    private LocalDateTime atndcDt;

    @Column(name = "atndc_yn")
    private String atndcYn; // 출석 여부 (Y/N)
}
