package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;


public interface SubCompetencyCategoryRepository extends JpaRepository<SubCompetencyCategory, Long> {


    List<SubCompetencyCategory> findByCoreCompetencyCategory_Id(Long coreCategoryId);

    List<SubCompetencyCategory> findByCoreCompetencyCategory_IdIn(List<Long> categoryIds);

    // assessmentId에 속한 모든 하위 역량을 조회하는 메소드 추가 (JPQL 사용)
    @Query("SELECT DISTINCT q.subCompetencyCategory FROM CoreCompetencyQuestion q WHERE q.assessment.id = :assessmentId")
    List<SubCompetencyCategory> findSubCategoriesByAssessmentId(@Param("assessmentId") Long assessmentId);

    SubCompetencyCategory findSubCompetencyCategoryById(Long id);

    Collection<Object> findByCoreCompetencyCategoryId(Long coreCompetencyCategoryId);
}
