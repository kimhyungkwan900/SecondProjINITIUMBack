package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResult;
import com.secondprojinitiumback.admin.coreCompetency.domain.StudentCompetencyScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCompetencyScoreRepository extends JpaRepository<StudentCompetencyScore,Long> {
    void deleteByResult(CoreCompetencyResult result);

    List<StudentCompetencyScore> findByResult(CoreCompetencyResult res);
}
