package com.secondprojinitiumback.admin.coreCompetency.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "student_competency_score")
public class StudentCompetencyScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RSULT_CTGR_SCR_ID")
    private Long id; // 학생 역량 점수 ID (PK)

    @ManyToOne
    @JoinColumn(name = "RSULT_ID", nullable = false)
    private CoreCompetencyResult result; // 진단 결과와 연관됨

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CTGR_ID", referencedColumnName = "CTGR_ID", nullable = false),
            @JoinColumn(name = "STGR_ID", referencedColumnName = "STGR_ID", nullable = false),
            @JoinColumn(name = "MPNG_ID", referencedColumnName = "MPNG_ID", nullable = false)
    })
    private CategoryQuestionMapping categoryQuestionMapping; // 문항-역량 매핑 정보

    @Column(name = "STD_CTGR_SCR")
    private BigDecimal standardScore; // 학생의 해당 역량 점수

    @Column(name = "TOT_CTGR_AVG_SCR")
    private BigDecimal totalAverageScore; // 전체 평균 점수
}
