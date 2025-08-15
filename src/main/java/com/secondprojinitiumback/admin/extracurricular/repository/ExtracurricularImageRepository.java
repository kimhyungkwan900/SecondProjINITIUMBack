package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ExtracurricularImageRepository extends JpaRepository<ExtracurricularImage,Integer> {

    List<ExtracurricularImage> findByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    ExtracurricularImage findExtracurricularImageByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    List<ExtracurricularImage> findExtracurricularImagesByExtracurricularProgram_EduMngId(Long extracurricularProgramEduMngId);

    @Query("""
    select i
    from ExtracurricularImage i
    where i.extracurricularProgram.eduMngId in :programIds
    order by i.extracurricularProgram.eduMngId asc, i.imgId asc
""")
    List<ExtracurricularImage> findAllByProgramIds(@Param("programIds") List<Long> programIds);

}
