package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoreCompetencyResponseRepository extends JpaRepository<CoreCompetencyResponse,Long> {
    List<CoreCompetencyResponse> findByStudentAndAssessment(Student student, CoreCompetencyAssessment assessment);

    List<CoreCompetencyResponse> findByAssessmentId(Long assessmentId);
}
