package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BehaviorIndicatorRepository extends JpaRepository<BehaviorIndicator, Long> {
    List<BehaviorIndicator> findBySubCompetencyCategory_Id(Long subCategoryId);
}
