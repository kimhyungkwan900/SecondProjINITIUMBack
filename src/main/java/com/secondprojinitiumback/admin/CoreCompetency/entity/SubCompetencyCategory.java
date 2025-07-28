package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@IdClass(SubCompetencyCategoryId.class)
@Entity
@Table(name = "sub_competency_category")
public class SubCompetencyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STGR_ID")
    private Long id; // 하위 역량 ID (복합키 1)

    @Id
    @Column(name = "CTGR_ID")
    private Long coreCategoryId; // 핵심 역량 카테고리 ID (복합키 2)

    @Column(name = "CTGR_NM")
    private String subCategoryName; // 하위 역량명

    @ManyToOne
    @JoinColumn(name = "CTGR_ID", insertable = false, updatable = false)
    private CoreCompetencyCategory coreCompetencyCategory; // 상위 카테고리와 연관 (읽기 전용)

}
