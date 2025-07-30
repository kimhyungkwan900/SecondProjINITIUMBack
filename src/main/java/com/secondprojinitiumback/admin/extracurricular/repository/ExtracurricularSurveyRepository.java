package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtracurricularSurveyRepository extends JpaRepository<ExtracurricularSurvey, Long> {

    boolean existsByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);
}
