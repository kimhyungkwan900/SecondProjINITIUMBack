package com.secondprojinitiumback.user.employee.repository;

import com.secondprojinitiumback.user.employee.domain.EmployeeStatusId;
import com.secondprojinitiumback.user.employee.domain.EmployeeStatusInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeStatusInfoRepository extends JpaRepository<EmployeeStatusInfo, EmployeeStatusId> {
    Optional<EmployeeStatusInfo> findByIdEmployeeStatusCodeAndIdEmployeeStatusCodeSe(
            String employeeStatusCode,
            String employeeStatusCodeSe
    );
}
