package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "RESPONSE_CHOICE_OPTION")
public class ResponseChoiceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHC_OPT_ID")
    private Long id; // 보기 ID

    @ManyToOne
    @JoinColumn(name = "QSTN_ID", insertable = false, updatable = false)
    private CoreCompetencyQuestion question; // 해당 문항과의 관계 (읽기 전용)

    @Column(name = "CHC_OPT_LBL")
    private String label; // 보기 라벨

    @Column(name = "OPT_SCR")
    private Integer score; // 해당 보기에 대한 점수
}
