package com.secondprojinitiumback.admin.coreCompetency.entity;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;

@Entity
@Table(name = "core_competency_response")
public class CoreCompetencyResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 ID (PK)

    // 학생 엔티티 연결
    @ManyToOne
    @JoinColumn(name = "STDNT_NO")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "QSTN_ID")
    private CoreCompetencyQuestion question; // 응답된 문항

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "CHC_OPT_ID", referencedColumnName = "CHC_OPT_ID"),
            @JoinColumn(name = "QSTN_ID", referencedColumnName = "QSTN_ID", insertable = false, updatable = false)
    })
    private ResponseChoiceOption selectedOption; // 선택한 객관식 보기 (복합키 기반)

    @ManyToOne
    @JoinColumn(name = "ASMT_ID")
    private CoreCompetencyAssessment assessment; // 소속된 진단 평가

    @Column(name = "CMPLET_DT")
    private String completeDate; // 응답 완료 일자

    @Column(name = "RSPNS_RSULT_SCR")
    private Integer resultScore; // 응답 결과 점수

    @Column(name = "RSPNS_RSULT_CN")
    private String resultContent; // 응답 결과 설명

    @Column(name = "SLCT_CNT")
    private Integer selectCount; // 선택된 보기 수
}

