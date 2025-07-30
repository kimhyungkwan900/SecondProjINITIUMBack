package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularSurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ExtracurricularSurveyResponseRepository extends JpaRepository<ExtracurricularSurveyResponse,Long> {

    Collection<Object> findExtracurricularSurveyResponseByExtracurricularSurvey_ExtracurricularProgram(ExtracurricularProgram extracurricularSurveyExtracurricularProgram);
}
