package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DscsnScheduleRepository extends JpaRepository<DscsnSchedule,String>, DscsnScheduleRepositoryCustom {
    DscsnSchedule findTopByDscsnDtIdStartingWithOrderByDscsnDtIdDesc(String prefix);
}
