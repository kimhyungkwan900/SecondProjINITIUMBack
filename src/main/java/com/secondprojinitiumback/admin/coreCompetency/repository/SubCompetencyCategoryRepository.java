package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubCompetencyCategoryRepository extends JpaRepository<SubCompetencyCategory, Long> {


    List<SubCompetencyCategory> findByCoreCompetencyCategory_Id(Long coreCategoryId);

    List<SubCompetencyCategory> findByCoreCompetencyCategory_IdIn(List<Long> categoryIds);

}
