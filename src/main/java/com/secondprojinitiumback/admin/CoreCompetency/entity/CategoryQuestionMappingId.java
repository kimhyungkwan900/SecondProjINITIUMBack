package com.secondprojinitiumback.admin.CoreCompetency.entity;

import java.io.Serializable;

import java.io.Serializable;

/**
 * CATEGORY_QUESTION_MAPPING 테이블의 복합키 식별 클래스
 * - MPNG_ID, STGR_ID, CTGR_ID 조합이 PK
 */
public class CategoryQuestionMappingId implements Serializable {
    private Long mappingId;
    private Long subCategoryId;
    private Long coreCategoryId;

    // equals(), hashCode() 구현 필수
}
