package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SubCompetencyCategoryRepository extends JpaRepository<SubCompetencyCategory, Long> {


    List<SubCompetencyCategory> findByCoreCompetencyCategory_Id(Long coreCategoryId);

    List<SubCompetencyCategory> findByCoreCompetencyCategory_IdIn(List<Long> categoryIds);

    // assessmentId에 속한 모든 하위 역량을 조회하는 메소드 추가 (JPQL 사용)
    @Query("SELECT DISTINCT q.subCompetencyCategory FROM CoreCompetencyQuestion q WHERE q.assessment.id = :assessmentId")
    List<SubCompetencyCategory> findSubCategoriesByAssessmentId(@Param("assessmentId") Long assessmentId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        UPDATE sub_competency_category
           SET DELETED_YN = 'Y',
               DELETED_AT = NOW()
         WHERE CTGR_ID = :categoryId
           AND (DELETED_YN IS NULL OR DELETED_YN = 'N')
        """, nativeQuery = true)
    int softDeleteByCategory(@Param("categoryId") Long categoryId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
        UPDATE sub_competency_category
           SET DELETED_YN = 'Y', DELETED_AT = NOW()
         WHERE STGR_ID = :subCategoryId
           AND (DELETED_YN IS NULL OR DELETED_YN = 'N')
    """, nativeQuery = true)
    int softDeleteBySubCategory(@Param("subCategoryId") Long subCategoryId);

}
