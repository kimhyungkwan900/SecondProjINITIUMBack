package com.secondprojinitiumback.user.employee.repository;

import com.secondprojinitiumback.user.employee.domain.EmployeeStatusInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeStatusInfoRepository extends JpaRepository<EmployeeStatusInfo, String> {
    Optional<EmployeeStatusInfo> findByEmployeeStatusCodeAndEmployeeStatusCodeSe(String code, String codeSe);
}
