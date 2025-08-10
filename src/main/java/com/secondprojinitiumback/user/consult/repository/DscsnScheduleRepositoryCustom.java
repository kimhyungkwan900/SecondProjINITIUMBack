package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;

import java.util.List;

public interface DscsnScheduleRepositoryCustom {
    List<DscsnSchedule> findDscsnSchedule(String startDay, String endDay, String dscsnType, String empNo);
}
