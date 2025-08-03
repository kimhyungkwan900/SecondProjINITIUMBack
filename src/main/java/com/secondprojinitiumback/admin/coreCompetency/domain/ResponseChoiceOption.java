package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "response_choice_option")
public class ResponseChoiceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHC_OPT_ID")
    private Long id; // 보기 ID

    @ManyToOne
    @JoinColumn(name = "QSTN_ID", referencedColumnName = "QSTN_ID", nullable = false)
    private CoreCompetencyQuestion question;

    @Column(name = "CHC_OPT_LBL")
    private String label; // 보기 라벨

    @Column(name = "OPT_SCR")
    private Integer score; // 해당 보기에 대한 점수
}
