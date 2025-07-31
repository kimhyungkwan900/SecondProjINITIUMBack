package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SUB_COMPETENCY_CATEGORY")
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
    private CoreCompetencyCategory coreCompetencyCategory;

}
