package com.secondprojinitiumback.user.employee.repository;

import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.dto.EmployeeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> search(EmployeeSearchDto employeeSearchDto);
    Page<Employee> searchPage(EmployeeSearchDto employeeSearchDto, Pageable pageable);
}
