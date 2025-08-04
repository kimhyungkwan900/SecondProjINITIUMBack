package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularSurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ExtracurricularSurveyResponseRepository extends JpaRepository<ExtracurricularSurveyResponse,Long> {

    // 특정 비교과 프로그램에 대한 모든 설문 응답 목록을 조회 (설문 응답 대상은 Object 타입으로 반환됨)
    Collection<Object> findExtracurricularSurveyResponseByExtracurricularSurvey_ExtracurricularProgram(ExtracurricularProgram extracurricularSurveyExtracurricularProgram);

    @Query("""
    select case when count(r) > 0 then true else false end
    from ExtracurricularSurveyResponse r
    join r.extracurricularSurvey s
    where s.extracurricularProgram.eduMngId = :eduMngId and r.student.studentNo = :stdntNo
    """)
        // 특정 학생이 특정 비교과 프로그램에 대한 설문 응답을 이미 제출했는지 여부를 확인
    boolean existsByEduMngIdAndStudent(@Param("eduMngId") Long eduMngId, @Param("studentNo") String studentNo);

}