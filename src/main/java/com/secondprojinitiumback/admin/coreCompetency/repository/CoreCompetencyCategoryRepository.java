package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoreCompetencyCategoryRepository extends JpaRepository<CoreCompetencyCategory, Long> {

    Object existsByCoreCategoryName(String categoryName);

    List<CoreCompetencyCategory> findByIdealTalentProfile_Id(Long id);
}
