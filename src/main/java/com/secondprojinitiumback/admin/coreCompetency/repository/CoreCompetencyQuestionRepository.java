package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoreCompetencyQuestionRepository extends JpaRepository<CoreCompetencyQuestion, Long> {

    /**
     * 특정 평가(assessmentId)에 해당하는 모든 문항(CoreCompetencyQuestion)을 조회하고,
     * 각 문항에 연결된 선택지(responseChoiceOptions)를 함께 즉시 로딩(FETCH JOIN)하는 쿼리.

     * - LEFT JOIN FETCH: 문항이 선택지를 가지고 있지 않아도 결과에 포함되도록 보장
     * - 지연 로딩(Lazy Loading)을 방지하여 N+1 문제 예방

     */
    @Query("SELECT q FROM CoreCompetencyQuestion q LEFT JOIN FETCH q.responseChoiceOptions " +
            "WHERE q.assessment.id = :assessmentId")
    List<CoreCompetencyQuestion> findAllWithOptionsByAssessmentId(@Param("assessmentId") Long assessmentId);
}
