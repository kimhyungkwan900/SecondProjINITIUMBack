package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyCategory;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyQuestion;
import com.secondprojinitiumback.admin.coreCompetency.domain.SubCompetencyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 핵심역량 문항/카테고리 조회용 리포지토리.
 */
public interface CoreCompetencyQuestionRepository extends JpaRepository<CoreCompetencyQuestion, Long> {

    /**
     * 특정 평가(assessmentId)에 속한 모든 문항과, 각 문항의 선택지를 함께 즉시 로딩.
     */
    @Query("SELECT DISTINCT q FROM CoreCompetencyQuestion q LEFT JOIN FETCH q.responseChoiceOptions " +
            "WHERE q.assessment.id = :assessmentId")
    List<CoreCompetencyQuestion> findAllWithOptionsByAssessmentId(@Param("assessmentId") Long assessmentId);

    /**
     * 하위역량(소분류) 기준으로 문항 조회.
     * 정렬: displayOrder ASC → questionNo ASC
     */
    List<CoreCompetencyQuestion> findBySubCompetencyCategory_IdOrderByDisplayOrderAscQuestionNoAsc(Long subCategoryId);

    /**
     * 평가 + 하위역량(소분류) 기준으로 문항 조회.
     * 정렬: displayOrder ASC
     */
    List<CoreCompetencyQuestion> findByAssessment_IdAndSubCompetencyCategory_IdOrderByDisplayOrderAsc(Long assessmentId, Long subId);

    /**
     * 특정 평가의 문항과 선택지를 함께 즉시 로딩하고,
     * 화면 표시에 맞게 정렬까지 보장.

     */
    @Query("SELECT DISTINCT q FROM CoreCompetencyQuestion q " +
            "LEFT JOIN FETCH q.responseChoiceOptions " +
            "WHERE q.assessment.id = :assessmentId " +
            "ORDER BY q.displayOrder ASC, q.questionNo ASC")
    List<CoreCompetencyQuestion> findQuestionsByAssessmentIdWithChoices(@Param("assessmentId") Long assessmentId);

    /**
     * 특정 평가의 상위 카테고리와 하위 카테고리를 한 번에 로딩.
     *
     */
    @Query("SELECT DISTINCT c FROM CoreCompetencyCategory c " +
            "LEFT JOIN FETCH c.subCompetencyCategories " +
            "WHERE c.assessment.id = :assessmentId " +
            "ORDER BY c.id ASC")
    List<CoreCompetencyCategory> findCategoriesByAssessmentIdWithSubCategories(@Param("assessmentId") Long assessmentId);
}
