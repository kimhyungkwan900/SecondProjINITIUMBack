package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyAssessment;
import com.secondprojinitiumback.admin.coreCompetency.domain.CoreCompetencyResponse;
import com.secondprojinitiumback.user.coreCompetency.dto.UserCoreCompetencyAssessmentDTO;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoreCompetencyResponseRepository extends JpaRepository<CoreCompetencyResponse,Long> {

    List<CoreCompetencyResponse> findByStudentAndAssessment(Student student, CoreCompetencyAssessment assessment);

    boolean existsByStudentAndAssessment(Student student, CoreCompetencyAssessment assessment);

    List<CoreCompetencyResponse> findAllWithDetailsByAssessment_AssessmentNo(String assessmentNo);

    List<CoreCompetencyResponse> findByStudent_StudentNoAndAssessment_AssessmentNo(String studentNo, String assessmentNo);

    List<CoreCompetencyResponse> findAllByAssessment_AssessmentNoAndStudent_StudentNo(String studentNo, String assessmentNo);

    //특정 진단에 대해, 특정 학생이 "응답한 기록이 존재하는가?"
    boolean existsByAssessment_IdAndStudent_StudentNo(Long assessmentId, String studentNo);

    // 특정 학생이 "응답한 모든 진단 ID"를 중복 없이 조회
    @Query("select distinct r.assessment.id " +
            "from CoreCompetencyResponse r " +
            "where r.student.studentNo = :studentNo")
    List<Long> findDistinctAssessmentIdsByStudentNo(@Param("studentNo") String studentNo);

    @Query("""
       select distinct a
       from CoreCompetencyResponse r
       join r.assessment a
       where r.student.studentNo = :studentNo
       order by a.endDate desc
       """)
    List<CoreCompetencyAssessment> findRespondedAssessments(@Param("studentNo") String studentNo);

    // 평가별 핵심역량 및 하위역량 평균
    @Query("""
        select sc.id, avg(coalesce(o.score,0))
        from CoreCompetencyResponse r
          join r.assessment a
          join r.question q
          join q.subCompetencyCategory sc
          left join r.selectedOption o
        where a.assessmentNo = :assessmentNo
        group by sc.id
        """)
    List<Object[]> findCohortAverageByAssessment(@Param("assessmentNo") String assessmentNo);
}
