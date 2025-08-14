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

        // 항목별 활성(Y) 정책 중 '하나'를 고정 규칙으로 가져오기 (id 오름차순)
        ScorePolicy findFirstByMileageItem_IdAndUseYnOrderByIdAsc(Long itemId, String useYn);

        // 바로 policyId만 받고 싶을 때 (null 가능)
        @Query("select s.id from ScorePolicy s " +
                "where s.mileageItem.id = :itemId and s.useYn = 'Y' order by s.id asc")
        Long findFirstActiveIdByItemId(@Param("itemId") Long itemId);
    }

