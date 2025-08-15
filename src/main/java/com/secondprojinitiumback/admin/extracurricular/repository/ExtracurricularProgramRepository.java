package com.secondprojinitiumback.admin.extracurricular.repository;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExtracurricularProgramRepository extends JpaRepository<ExtracurricularProgram, Long>,
        JpaSpecificationExecutor<ExtracurricularProgram> {

    List<ExtracurricularProgram> findByEduEndYmdBeforeAndSttsNm(LocalDate date, SttsNm SttsNm);

    List<ExtracurricularProgram> findByEduBgngYmdBeforeAndSttsNm(LocalDate eduBgngYmdBefore, SttsNm sttsNm);

    Optional<ExtracurricularProgram> findByEduMngId(Long eduMngId);

    // === 기존: 하위역량 id 집합으로 연결된 프로그램
    @Query("""
        select distinct p
        from ExtracurricularProgram p
        join p.extracurricularCategory c
        where c.stgrId in :subIds
    """)
    List<ExtracurricularProgram> findProgramsBySubIds(@Param("subIds") Collection<Long> subIds);

    // === 신규: 정책(승인 + 마감 미도래) 포함 + 페이징/정렬
    @Query("""
        select distinct p
        from ExtracurricularProgram p
        join p.extracurricularCategory c
        where c.stgrId in :subIds
          and p.sttsNm = com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm.APPROVED
          and (p.eduAplyEndDt is null or p.eduAplyEndDt >= CURRENT_TIMESTAMP)
    """)
    Page<ExtracurricularProgram> findRecommendedBySubIdsWithPolicy(
            @Param("subIds") Collection<Long> subIds,
            Pageable pageable
    );
}
