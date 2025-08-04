package com.secondprojinitiumback.user.employee.service.serviceinterface;

import com.secondprojinitiumback.user.employee.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    // 교수 임용 및 직원 고용
    EmployeeDto appointProfessor(ProfessorAppointDto dto);
    EmployeeDto appointInstructor(CounselorHireDto dto);
    EmployeeDto appointStaff(StaffAppointDto dto);

    // 직원 정보 수정
    EmployeeDto adminUpdateEmployeeInfo(String employeeNo, AdminUpdateEmployeeDto dto);
    EmployeeDto updateMyInfo(String employeeNo, EmployeeUpdateMyInfoDto dto);

    // 교직원 상태 변경
    EmployeeDto changeStatus(String employeeNo, String statusCode);

    // 교직원 조회
    EmployeeDto getEmployee(String employeeNo);
    List<EmployeeDto> getEmployeeList(EmployeeSearchDto employeeSearchDto);
    Page<EmployeeDto> getEmployeePage(EmployeeSearchDto employeeSearchDto, Pageable pageable);

    // 퇴사 처리 (내부적으로는 상태 변경을 사용)
    void resignEmployee(String employeeNo);
}