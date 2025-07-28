package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "QSTN_SCSBJT_MAP")
public class QuestionSubjectMapping {

    @Id
    @Column(name = "QSTN_SCSBJT_MAP_ID")
    private Long id; // 매핑 ID (PK)

    @ManyToOne
    @JoinColumn(name = "QSTN_ID")
    private CoreCompetencyQuestion question; // 매핑된 문항

    // 추후 학과 테이블과 연결 예정
//    @ManyToOne
//    @JoinColumn(name = "SCSBJT_NO")
//    private Subject subject;
}
