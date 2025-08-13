package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "core_competency_result")
@NoArgsConstructor
@AllArgsConstructor
public class CoreCompetencyResult {

    @Id
    @Column(name = "RSULT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 결과 ID (PK)

    @ManyToOne
    @JoinColumn(name = "ASMT_ID")
    private CoreCompetencyAssessment assessment; // 관련된 평가

    //학생 엔티티와 연결
    @ManyToOne
    @JoinColumn(name = "STDNT_NO")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "ASMT_RSPNS_ID")
    private CoreCompetencyResponse response; // 해당 평가에서 학생의 응답

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CLSF_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "CLSF_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode classificationResult;

    @Builder.Default
    @Column(name = "CLSF_GRP", insertable = false, updatable = false)
    private String classificationResultGroup = "CLSF";

}

