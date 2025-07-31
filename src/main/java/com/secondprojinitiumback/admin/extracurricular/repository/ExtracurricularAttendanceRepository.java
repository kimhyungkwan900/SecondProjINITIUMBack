package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularAttendance;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExtracurricularAttendanceRepository extends JpaRepository<ExtracurricularAttendance, Long> {
    // 특정 교육 일정 ID와 학생 번호로 비교과 활동 출석 정보 조회
    Optional<ExtracurricularAttendance> findExtracurricularAttendancesByExtracurricularSchedule_ExtracurricularProgram_eduMngIdAndStdntInfo_StdntNo(Long eduShdlId, String stdntNo);

    Optional<ExtracurricularAttendance> findByExtracurricularScheduleAndStdntInfo(ExtracurricularSchedule schedule, StdntInfo stdntInfo);

    @Query("""
    select 
        (count(case when ea.atndcYn = 'Y' then 1 end) * 1.0) / count(ea)
    from ExtracurricularAttendance ea
    join ea.extracurricularSchedule es
    join es.extracurricularProgram ep
    where ep.eduMngId = :eduMngId and ea.stdntInfo.stdntNo = :stdntNo
""")
    Double calculateAttendanceRate(@Param("eduMngId") Long eduMngId, @Param("stdntNo") String stdntNo);
}
