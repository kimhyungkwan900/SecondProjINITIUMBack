package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoreCompetencyResultRepository extends JpaRepository<CoreCompetencyResult, Long> {
    List<CoreCompetencyResult> findByAssessment(CoreCompetencyAssessment assessment);
}

