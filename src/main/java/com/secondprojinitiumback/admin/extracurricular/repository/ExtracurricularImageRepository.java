package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ExtracurricularImageRepository extends JpaRepository<ExtracurricularImage,Integer> {

    List<ExtracurricularImage> findByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    ExtracurricularImage findExtracurricularImageByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    List<ExtracurricularImage> findExtracurricularImagesByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);
}
