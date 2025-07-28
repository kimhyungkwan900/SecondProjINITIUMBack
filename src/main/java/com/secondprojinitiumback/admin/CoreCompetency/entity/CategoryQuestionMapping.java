package com.secondprojinitiumback.admin.CoreCompetency.entity;

import jakarta.persistence.*;

@Entity
@IdClass(CategoryQuestionMappingId.class)
@Table(name = "CATEGORY_QUESTION_MAPPING")
public class CategoryQuestionMapping {

    @Id
    @Column(name = "MPNG_ID")
    private Long mappingId; // 매핑 고유 ID

    @Id
    @Column(name = "STGR_ID")
    private Long subCategoryId; // 하위역량 ID

    @Id
    @Column(name = "CTGR_ID")
    private Long coreCategoryId; // 상위역량 ID

    @ManyToOne
    @JoinColumn(name = "QSTN_ID")
    private CoreCompetencyQuestion question; // 해당 질문과 연결됨

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "STGR_ID", referencedColumnName = "STGR_ID", insertable = false, updatable = false),
            @JoinColumn(name = "CTGR_ID", referencedColumnName = "CTGR_ID", insertable = false, updatable = false)
    })
    private SubCompetencyCategory subCompetencyCategory; // 하위/상위역량 조합 정보

}
