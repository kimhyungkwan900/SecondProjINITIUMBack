package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "core_competency_question")
public class CoreCompetencyQuestion {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "QSTN_ID")
    private Long id; // 질문 ID

    @ManyToOne
    @JoinColumn(name = "ASMT_ID")
    private CoreCompetencyAssessment assessment;

    @Column(name = "QSTN_NO")
    private Integer questionNo;

    @Column(name = "QSTN_NM")
    private String name;

    @Column(name = "DSPY_ORD_NO")
    private Integer displayOrder;

    @Column(name = "ANSR_ALOW_CNT")
    private Integer answerAllowCount;

    @Column(name = "IS_COMMON")
    private String isCommon;
}
