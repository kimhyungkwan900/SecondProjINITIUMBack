package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DscsnInfoRepository extends JpaRepository<DscsnInfo, String> , DscsnInfoRepositoryCustom{
    DscsnInfo findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc(String prefix);
}
