package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ExtracurricularImageRepository extends JpaRepository<ExtracurricularImage,Integer> {

    Collection<Object> findByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);
}
