package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CoreCompetencyCategoryRepository extends JpaRepository<CoreCompetencyCategory, Long> {

    boolean existsByCoreCategoryName(String categoryName);

    List<CoreCompetencyCategory> findByAssessment_Id(Long assessmentId);

}
