package com.secondprojinitiumback.admin.coreCompetency.entity;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CORE_COMPETENCY_ASSESSMENT")
public class CoreCompetencyAssessment {

    @Id
    @Column(name = "ASMT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 평가 ID (PK)

    @ManyToOne
    @JoinColumn(name = "STGR_ID")
    private SubCompetencyCategory subCategory; // 하위역량 카테고리 (FK)

    // 학과 정보 연결
    @ManyToOne
    @JoinColumn(name = "SCSBJT_NO")
    private SchoolSubject schoolSubject;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "STERM_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "STERM_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode semesterCode; // 학기 정보 (FK)

    @Column(name = "STERM_GRP", insertable = false, updatable = false)
    private String semesterGroup = "SEMESTER"; // 학기 구분 코드 (고정값)


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ONLNE_EXEC_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "ONLNE_EXEC_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode onlineExecCode;  // 온라인 실행 여부 (고정값: Y/N)

    @Column(name = "ONLNE_EXEC_GRP", insertable = false, updatable = false)
    private String onlineExecGroupCode = "ONLINE_YN";


    @Lob
    @Column(name = "GUID_CN")
    private String guideContent; // 평가 안내문

    @Lob
    @Column(name = "CORE_CMPT_AGRM_CN")
    private String agreementContent; // 동의 문구

}

