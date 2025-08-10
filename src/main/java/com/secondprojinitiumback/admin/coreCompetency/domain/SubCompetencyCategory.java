package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secondprojinitiumback.common.domain.CommonCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sub_competency_category")
public class SubCompetencyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STGR_ID")
    private Long id; // 하위 역량 ID


    @Column(name = "STGR_NM", nullable = false)
    private String subCategoryName; // 하위 역량명

    @Column(name = "STGR_CN", nullable = false)
    private String subCategoryNote; // 하위 역량 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CTGR_ID", nullable = false)
    @JsonIgnore
    private CoreCompetencyCategory coreCompetencyCategory;

    //양방향 설정
    @OneToMany(mappedBy = "subCompetencyCategory", fetch = FetchType.LAZY)
    private List<BehaviorIndicator> behaviorIndicators = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "COMP_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "COMP_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode competencyCategory;

    @Builder.Default
    @Column(name = "COMP_GRP", insertable = false, updatable = false)
    private String competencyCategoryGroup = "COMP";




}
