package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import java.util.List;

public interface CoreCompetencyAssessmentRepository extends JpaRepository<CoreCompetencyAssessment, Long> {

    @Query("SELECT a FROM CoreCompetencyAssessment a " +
            "WHERE (:year IS NULL OR a.academicYear = :year) " +
            "AND (:semester IS NULL OR a.semesterCode.codeName = :semester) " +
            "AND (:assessmentNo IS NULL OR a.assessmentNo = :assessmentNo)")
    List<CoreCompetencyAssessment> findByConditions(@Param("year") String year,
                                                    @Param("semester") String semester,
                                                    @Param("assessmentNo") String assessmentNo);


    Optional<CoreCompetencyAssessment> findByAssessmentNo(String assessmentNo);
}
