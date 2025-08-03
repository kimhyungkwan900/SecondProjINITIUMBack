package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "recommended_program")
public class RecommendedProgram {

    @Id
    @Column(name = "KeRCMD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 추천 ID (PK)

    // 학생 및 비교과 프로그램과 연관
    @ManyToOne
    @JoinColumn(name = "STDNT_NO")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "RSULT_ID")
    private CoreCompetencyResult result; // 핵심역량 결과와 연관

    @ManyToOne
    @JoinColumn(name = "ITP_ID")
    private IdealTalentProfile idealTalentProfile; // 인재상 프로필과 연관

    @ManyToOne
    @JoinColumn(name = "EDU_MNG_ID")
    private ExtracurricularProgram program;

    @Lob
    @Column(name = "RCMD_RSN_CN")
    private String reasonContent; // 추천 사유

}

