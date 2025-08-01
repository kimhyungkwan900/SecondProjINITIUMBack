package com.secondprojinitiumback.admin.coreCompetency.domain;

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
@Table(name = "BEHAVIOR_INDICATOR")
public class BehaviorIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INDCTR_ID")
    private Long id; // 행동 지표 ID (기본 키)

    @Column(name = "INDCTR_NM", nullable = false)
    private String name; // 행동 지표 이름 (예: "적극성", "협업 능력" 등)

    @ManyToOne
    @JoinColumn(name = "STGR_ID", nullable = false)
    private SubCompetencyCategory subCompetencyCategory; // 하위 역량 카테고리 (외래 키: 하위 역량 ID)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "IS_COMMON", referencedColumnName = "CD"),
            @JoinColumn(name = "IS_COMMON_CD_SE", referencedColumnName = "CD_SE")
    })
    private CommonCode isCommonCode; // 공통 여부 (공통 문항인지 여부를 나타내는 코드)

    @Builder.Default
    @Column(name = "IS_COMMON_CD_SE", insertable = false, updatable = false)
    private String isCommonGroupCode = "ComYN";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject; // 학과
}
