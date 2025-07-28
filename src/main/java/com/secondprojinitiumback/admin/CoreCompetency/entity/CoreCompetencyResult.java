package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CORE_COMPETENCY_RESULT")
public class CoreCompetencyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결과 ID (PK)

    @ManyToOne
    @JoinColumn(name = "ASMT_ID")
    private CoreCompetencyAssessment assessment; // 관련된 평가

    // 추후 학생 엔티티와 연결 예정
//    @ManyToOne
//    @JoinColumn(name = "STDNT_NO")
//    private Student student;

    @ManyToOne
    @JoinColumn(name = "ASMT_RSPNS_ID")
    private CoreCompetencyResponse response; // 해당 평가에서 학생의 응답

    @Column(name = "CLSF_RSULT_CD")
    private String classificationCode; // 결과 분류 코드
}

