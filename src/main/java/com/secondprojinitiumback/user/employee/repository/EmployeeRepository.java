package com.secondprojinitiumback.user.employee.repository;

import com.secondprojinitiumback.user.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, EmployeeRepositoryCustom {
    // 시퀸스용 번호조회
    Optional<String> findTopByEmpNoStartingWithOrderByEmpNoDesc(String empNoPrefix);

    // 사번/교번으로 조회
    Optional<Employee> findByEmpNo(String empNo);

    // 로그인 ID 로 조회
    Optional<Employee> findByLoginInfoLoginId(String loginId);

    // 이메일로 조회
    Optional<Employee> findByEmail(String email);

    List<Employee> findByEmpNoStartingWith(String empNoPrefix);
}
