package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.entity.SubCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCompetencyCategoryRepository extends JpaRepository<SubCompetencyCategory, Long> {


    List<SubCompetencyCategory> findByCoreCategoryId(Long coreCategoryId);
}
