package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtracurricularProgramRepository extends JpaRepository<ExtracurricularProgram, Long> {

}
