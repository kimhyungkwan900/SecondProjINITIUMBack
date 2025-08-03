package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "SUB_CTGR_ID")
    private SubCompetencyCategory subCategory;

    @Column(name = "STD_CTGR_SCR")
    private BigDecimal standardScore; // 학생의 해당 역량 점수

    @Column(name = "TOT_CTGR_AVG_SCR")
    private BigDecimal totalAverageScore; // 전체 평균 점수

    @Column(name = "CLSF_RSULT_CD")
    private String classificationCode; // 결과 분류 코드

}
