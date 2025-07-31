package com.secondprojinitiumback.admin.coreCompetency.repository;

import com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 행동지표(BehaviorIndicator) 엔티티에 대한 Repository
 * - 공통 여부 및 전공에 따라 조회 기능 제공
 */
public interface BehaviorIndicatorRepository extends JpaRepository<BehaviorIndicator, Long> {

    /**
     * 공통 여부가 Y 또는 N인 행동지표 전체 조회
     *
     * @param isCommon 공통 여부 코드 ("Y" 또는 "N")
     * @return 해당 조건의 행동지표 리스트
     */
    List<BehaviorIndicator> findByIsCommon(String isCommon);

    /**
     * 공통 여부 + 전공 코드로 행동지표 조회
     *
     * @param isCommon 공통 여부 코드 ("Y" 또는 "N")
     * @param subject  전공 엔티티
     * @return 해당 조건의 행동지표 리스트
     */
    @Query("SELECT b FROM BehaviorIndicator b WHERE b.isCommonCode.id.code = :isCommon AND b.schoolSubject = :subject")
    List<BehaviorIndicator> findByIsCommonAndSchoolSubject(@Param("isCommon") String isCommon, @Param("subject") SchoolSubject subject);
}
