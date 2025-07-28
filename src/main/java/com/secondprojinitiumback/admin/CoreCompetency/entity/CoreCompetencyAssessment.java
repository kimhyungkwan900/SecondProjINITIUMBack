package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "core_competency_assessment")
public class CoreCompetencyAssessment {

    @Id
    @Column(name = "ASMT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 평가 ID (PK)

    @ManyToOne
    @JoinColumn(name = "CTGR_ID")
    private CoreCompetencyCategory CoreCategory; // 핵심역량 카테고리 (FK)

    @ManyToOne
    @JoinColumn(name = "STGR_ID")
    private SubCompetencyCategory subCategory; // 하위역량 카테고리 (FK)

    // 추후 연결 예정: 학과 정보
//    @ManyToOne
//    @JoinColumn(name = "SCSBJT_NO")
//    private Subject subject;

    @Column(name = "ASMT_NO")
    private String assessmentNo; // 평가 코드

    @Column(name = "ASMT_NM")
    private String assessmentName; // 평가명

    @Column(name = "ASMT_STRT_DT")
    private String startDate; // 평가 시작일

    @Column(name = "ASMT_END_DT")
    private String endDate; // 평가 종료일

    @Column(name = "RGST_DT")
    private String registerDate; // 등록일

    @Column(name = "ACAD_YR")
    private String academicYear; // 학년도

    @Column(name = "STERM_CD")
    private String semesterCode; // 학기 코드

    @Column(name = "ONLNE_EXEC_YN")
    private String onlineYn; // 온라인 평가 여부

    @Lob
    @Column(name = "GUID_CN")
    private String guideContent; // 평가 안내문

    @Lob
    @Column(name = "CORE_CMPT_AGRM_CN")
    private String agreementContent; // 동의 문구
}

