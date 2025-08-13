package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnSatisfaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DscsnSatisfactionRepository extends JpaRepository<DscsnSatisfaction,String> {
    DscsnSatisfaction findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc(String prefix);

    boolean existsByDscsnInfo_DscsnInfoId(String dscsnInfoId);
}
