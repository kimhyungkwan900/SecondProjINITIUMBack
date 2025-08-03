package com.secondprojinitiumback.user.employee.service.serviceinterface;

import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.dto.*;

import java.util.List;

public interface EmployeeService {

    // 교수 임용 및 직원 고용
    void appointProfessor(ProfessorAppointDto dto);
    void appointInstructor(CounselorHireDto dto);
    void appointStaff(StaffAppointDto dto);
    void appointEmployee(EmployeeAppointDto dto, String rolePrefix, String userType);

    // 직원 정보 수정
    void adminUpdateEmployeeInfo(String employeeNo, AdminUpdateEmployeeDto dto);
    void updateMyInfo(String employeeNo, EmployeeUpdateMyInfoDto dto);

    // 교직원 검색
    EmployeeSearchDto getEmployee(String employeeNo);
    List<EmployeeDto> getEmployeeList(EmployeeSearchDto employeeSearchDto);

    // 퇴사 처리
    void resignEmployee(String employeeNo);
}
