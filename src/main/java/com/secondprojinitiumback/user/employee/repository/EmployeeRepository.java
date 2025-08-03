package com.secondprojinitiumback.user.employee.repository;

import com.secondprojinitiumback.user.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, EmployeeRepositoryCustom {
    Optional<Employee> findByEmployeeNo(String employeeNo);
    Optional<String> findTopByEmpNoStartingWithOrderByEmpNoDesc(String empNoPrefix);

    Optional<Employee> findByLoginInfoLoginId(String loginId);

    Optional<Employee> findByEmail(String email);
}
