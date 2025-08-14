package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "core_competency_assessment")
@SQLDelete(sql = "UPDATE core_competency_assessment SET DELETED_YN='Y', DELETED_AT=NOW() WHERE ASMT_ID=?")
@Where(clause = "DELETED_YN = 'N'") // 기본 조회에서 삭제된 행 제외
public class CoreCompetencyAssessment {

    @Id
    @Column(name = "ASMT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 평가 ID (PK)

    // 학과(부서) 정보 연결
    @ManyToOne
    @JoinColumn(name = "SCSBJT_NO")
    private SchoolSubject schoolSubject;

    @Column(name = "ASMT_NO")
    private String assessmentNo; // 진단번호

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

    @Column(name = "CAHRT_TP")
    private String chartType = "개인별+전체"; // 차트 표시 선택

    @Column(name = "ALYS_TP")
    private String analysisType = "평균"; // 분석 기준

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "STERM_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "STERM_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode semesterCode; // 학기 정보 (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ONLNE_EXEC_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "ONLNE_EXEC_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode onlineExecCode;  // 온라인 실행 여부 (고정값: Y/N)

    @Lob
    @Column(name = "GUID_CN")
    private String guideContent; // 평가 안내문

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoreCompetencyCategory> coreCompetencyCategories = new ArrayList<>();

    //물리적 삭제 대신 소프트 삭제를 위함 - 진단이 삭제돼도 응답 결과를 확인하기 위함
    // 진단 삭제는 플래그만 올리고, 조회 API에서만 숨김처리하도록
    @Column(name="DELETED_YN", nullable=false) private String deletedYn = "N";
    @Column(name="DELETED_AT") private LocalDateTime deletedAt;


}
