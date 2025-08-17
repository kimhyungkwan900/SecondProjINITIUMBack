package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DscsnInfoRepository extends JpaRepository<DscsnInfo, String> , DscsnInfoRepositoryCustom{
    DscsnInfo findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc(String prefix);
    
    // empNo 기반으로 상담내역 조회
    @Query("SELECT di FROM DscsnInfo di WHERE di.dscsnApply.dscsnDt.employee.empNo = :empNo ORDER BY di.dscsnApply.dscsnDt.possibleDate DESC, di.dscsnApply.dscsnDt.possibleTime DESC")
    List<DscsnInfo> findByEmpNo(@Param("empNo") String empNo);
}
