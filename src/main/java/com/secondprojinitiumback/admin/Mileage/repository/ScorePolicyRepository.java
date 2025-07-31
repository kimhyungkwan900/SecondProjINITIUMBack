package com.secondprojinitiumback.admin.Mileage.repository;

import com.secondprojinitiumback.admin.Mileage.domain.ScorePolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

    @Repository
    public interface ScorePolicyRepository extends JpaRepository<ScorePolicy, Long> {

        @Query("SELECT s FROM ScorePolicy s " +
                "WHERE (:eduNm IS NULL OR s.program.eduNm LIKE %:eduNm%)")
        Page<ScorePolicy> searchByEduNm(@Param("eduNm") String eduNm, Pageable pageable);
    }

