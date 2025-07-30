package com.secondprojinitiumback.admin.extracurricular.domain;


import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "extracurricular_team_member")
public class ExtracurricularTeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_mng_id")
    private Long teamMngId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private ExtracurricularTeam extracurricularTeam; // 팀 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stdnt_no", nullable = false)
    private StdntInfo stdntInfo; // 학생 정보

    @Column(name = "team_qlfc_yn")
    private String teamQlfcYn; // 팀 소속 여부 (Y/N)
}
