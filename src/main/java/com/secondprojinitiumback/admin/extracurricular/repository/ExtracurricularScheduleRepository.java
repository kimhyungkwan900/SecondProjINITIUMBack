package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtracurricularScheduleRepository extends JpaRepository<ExtracurricularSchedule, Long> {

    List<ExtracurricularSchedule> findExtracurricularSchedulesByExtracurricularProgram_eduMngId(Long extracurricularProgramEduMngId);

    List<ExtracurricularSchedule> findExtracurricularSchedulesByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    List<ExtracurricularSchedule> findByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);
}
