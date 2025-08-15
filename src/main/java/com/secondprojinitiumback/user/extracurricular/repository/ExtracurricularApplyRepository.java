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

@Repository
public interface ExtracurricularApplyRepository extends JpaRepository<ExtracurricularApply,Long>, JpaSpecificationExecutor<ExtracurricularApply> {

    // 특정 학생이 특정 비교과 프로그램에 이미 신청했는지 여부 확인
    boolean existsBystudentAndExtracurricularProgram(Student student, ExtracurricularProgram extracurricularProgram);

    // 특정 비교과 프로그램에 대해 승인된(AprySttsNm 기준) 신청자의 수를 조회
    long countByExtracurricularProgramAndAprySttsNm(ExtracurricularProgram extracurricularProgram, AprySttsNm aprySttsNm);

    // 학생 번호와 삭제 여부를 기준으로 해당 학생의 신청 내역 조회 (삭제된 내역 포함 여부 판단 가능)
    Page<ExtracurricularApply> findByStudent_StudentNoAndDelYn(String stdntNo, String delYn, Pageable pageable);


    List<ExtracurricularApply> findByStudent_StudentNo(String studentNo);


    Page<ExtracurricularApply> findByStudent_StudentNoAndAprySttsNmAndDelYn(String studentStudentNo, AprySttsNm aprySttsNm, String delYn,  Pageable pageable);

    // 비교과 프로그램 ID와 신청 상태를 기준으로 신청 내역 조회 (특정 프로그램의 승인자/대기자 등 필터링 가능)
    List<ExtracurricularApply> findByExtracurricularProgram_EduMngIdAndAprySttsNm(Long eduMngId, AprySttsNm aprySttsNm);

    int countByExtracurricularProgram_EduMngIdAndAprySttsNm(Long extracurricularProgramEduMngId, AprySttsNm aprySttsNm);
    // 위와 동일: 프로그램 ID와 신청 상태를 기준으로 신청 내역 조회 (메서드 명명 방식만 다름)
    List<ExtracurricularApply> findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndAprySttsNm(Long eduMngId, AprySttsNm statusNm);

    // 비교과 프로그램 ID와 학생 번호를 기준으로 특정 학생의 신청 내역을 Optional로 조회 (단일 결과 기대 시 사용)
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
}
