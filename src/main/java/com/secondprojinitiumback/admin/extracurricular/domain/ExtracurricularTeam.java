package com.secondprojinitiumback.admin.extracurricular.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.time.LocalDateTime;

@Entity
@Table(name = "extracurricular_team")
@Getter
@Setter
public class ExtracurricularTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_mng_id", nullable = false)
    private ExtracurricularProgram extracurricularProgram; // 비교과 프로그램 ID

    @Column(name = "team_nope")
    private int teamNope; // 전체 팀 인원

    @Column(name = "team_crt_ymd")
    private LocalDateTime teamCrtYmd; // 팀 생성일
}
