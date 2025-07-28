package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "RECOMMENDED_PROGRAM")
public class RecommendedProgram {

    @Id
    @Column(name = "RCMD_ID")
    private String id; // 추천 ID (PK)

    // 추후 학생 및 비교과 프로그램과 연관 예정
//    @ManyToOne
//    @JoinColumn(name = "STDNT_NO")
//    private Student student;
//
//    @ManyToOne
//    @JoinColumn(name = "EDU_MNG_ID")
//    private ExtracurricularProgram program;

    @Lob
    @Column(name = "RCMD_RSN_CN")
    private String reasonContent; // 추천 사유
}

