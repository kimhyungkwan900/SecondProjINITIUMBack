package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicatorMajorQuestionMapping;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BehaviorIndicatorMajorQuestionMappingRepository extends JpaRepository<BehaviorIndicatorMajorQuestionMapping, Long> {

    BehaviorIndicatorMajorQuestionMapping findByQuestionAndBehaviorIndicatorAndSchoolSubject(CoreCompetencyQuestion savedQuestion, BehaviorIndicator behaviorIndicator, SchoolSubject schoolSubject);

    Optional<BehaviorIndicatorMajorQuestionMapping> findByQuestion(CoreCompetencyQuestion savedQuestion);
}
