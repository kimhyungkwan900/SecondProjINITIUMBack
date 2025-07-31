package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DscsnApplyRepoistory extends JpaRepository<DscsnApply,String> {
    DscsnApply findTopByDscsnApplyIdStartingWithOrderByDscsnApplyIdDesc(String prefix);
}
