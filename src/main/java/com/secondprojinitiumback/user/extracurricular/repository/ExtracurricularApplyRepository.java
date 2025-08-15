package com.secondprojinitiumback.user.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.student.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExtracurricularApplyRepository extends JpaRepository<ExtracurricularApply,Long>, JpaSpecificationExecutor<ExtracurricularApply> {

    boolean existsBystudentAndExtracurricularProgram(Student student, ExtracurricularProgram extracurricularProgram);

    long countByExtracurricularProgramAndAprySttsNm(ExtracurricularProgram extracurricularProgram, AprySttsNm aprySttsNm);

    Page<ExtracurricularApply> findByStudent_StudentNoAndDelYn(String stdntNo, String delYn, Pageable pageable);

    List<ExtracurricularApply> findByStudent_StudentNo(String studentNo);

    Page<ExtracurricularApply> findByStudent_StudentNoAndAprySttsNmAndDelYn(String studentStudentNo, AprySttsNm aprySttsNm, String delYn,  Pageable pageable);

    List<ExtracurricularApply> findByExtracurricularProgram_EduMngIdAndAprySttsNm(Long eduMngId, AprySttsNm aprySttsNm);

    int countByExtracurricularProgram_EduMngIdAndAprySttsNm(Long extracurricularProgramEduMngId, AprySttsNm aprySttsNm);

    List<ExtracurricularApply> findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndAprySttsNm(Long eduMngId, AprySttsNm statusNm);

    Optional<ExtracurricularApply> findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStudent_studentNo(Long eduMngId, String stdntNo);

    List<ExtracurricularApply> findByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    Page<ExtracurricularApply> findByStudent_StudentNo(String studentNo, Pageable pageable);

    Boolean existsBystudentAndExtracurricularProgram_EduMngId(Student student, Long extracurricularProgramEduMngId);

    @Query("""
        select e from ExtracurricularApply e
        join e.extracurricularProgram p
        join p.extracurricularCategory c
        where e.student.studentNo = :stdntNo
          and e.delYn = 'N'
          and (:aprySttsNm is null or e.aprySttsNm = :aprySttsNm)
          and (:keyword is null or p.eduNm like %:keyword% or c.ctgryNm like %:keyword%)
    """)
    Page<ExtracurricularApply> findApplyWithFilters(
            @Param("stdntNo") String stdntNo,
            @Param("aprySttsNm") AprySttsNm aprySttsNm,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("""
        select a.extracurricularProgram.eduMngId, count(a)
        from ExtracurricularApply a
        where a.extracurricularProgram.eduMngId in :programIds
          and a.aprySttsNm = com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm.ACCEPT
          and a.delYn = 'N'
        group by a.extracurricularProgram.eduMngId
    """)
    List<Object[]> countAcceptedByProgramIds(@Param("programIds") List<Long> programIds);

    // === 신규: 해당 학생이 이미 신청(대기/승인) 중인 프로그램 ID 집합
    @Query("""
        select distinct a.extracurricularProgram.eduMngId
        from ExtracurricularApply a
        where a.student.studentNo = :studentNo
          and a.delYn = 'N'
          and a.aprySttsNm in (
              com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm.APPLY,
              com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm.ACCEPT
          )
    """)
    Set<Long> findAppliedOrCompletedProgramIds(@Param("studentNo") String studentNo);
}
