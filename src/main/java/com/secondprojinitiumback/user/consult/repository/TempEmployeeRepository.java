package com.secondprojinitiumback.user.consult.repository;

import com.secondprojinitiumback.user.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempEmployeeRepository extends JpaRepository<Employee, String> {
    Employee getEmployeeByEmpNo(String empNo);
}
