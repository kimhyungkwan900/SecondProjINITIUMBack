package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CoreCompetencyQuestionRepository extends JpaRepository<CoreCompetencyQuestion, Long> {

    List<CoreCompetencyQuestion> findByAssessment_Id(Long assessmentId);
}
