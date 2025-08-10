package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExtracurricularProgramRepository extends JpaRepository<ExtracurricularProgram, Long>,
        JpaSpecificationExecutor<ExtracurricularProgram> {

    List<ExtracurricularProgram> findByEduEndYmdBeforeAndSttsNm(LocalDate date, SttsNm SttsNm);

    List<ExtracurricularProgram> findByEduBgngYmdBeforeAndSttsNm(LocalDate eduBgngYmdBefore, SttsNm sttsNm);

    Optional<ExtracurricularProgram> findByEduMngId(Long eduMngId);
}

