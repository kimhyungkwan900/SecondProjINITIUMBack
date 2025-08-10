package com.secondprojinitiumback.user.employee.controller;

import com.secondprojinitiumback.user.employee.dto.*;
import com.secondprojinitiumback.user.employee.service.serviceinterface.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import com.secondprojinitiumback.common.dto.request.StatusChangeRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // 교직원 임용 (신규 등록)
    @PostMapping("/appoint/professor")
    public ResponseEntity<EmployeeDto> appointProfessor(@RequestBody ProfessorAppointDto dto) {
        // 교수 임용 처리
        EmployeeDto createdEmployee = employeeService.appointProfessor(dto);

        // 생성된 교직원 정보의 URI를 생성하여 응답
        URI location = URI.create(String.format("/api/employees/%s", createdEmployee.getEmpNo()));

        // ResponseEntity를 사용하여 201 Created 상태와 함께 생성된 교직원 정보를 반환
        return ResponseEntity.created(location).body(createdEmployee);
    }

    // 교직원 임용 (신규 등록)
    @PostMapping("/appoint/instructor")
    public ResponseEntity<EmployeeDto> appointInstructor(@RequestBody CounselorHireDto dto) {
        // 상담교사 임용 처리
        EmployeeDto createdEmployee = employeeService.appointInstructor(dto);
        URI location = URI.create(String.format("/api/employees/%s", createdEmployee.getEmpNo()));
        return ResponseEntity.created(location).body(createdEmployee);
    }

    // 교직원 임용 (신규 등록)
    @PostMapping("/appoint/staff")
    public ResponseEntity<EmployeeDto> appointStaff(@RequestBody StaffAppointDto dto) {
        // 직원 임용 처리
        EmployeeDto createdEmployee = employeeService.appointStaff(dto);
        URI location = URI.create(String.format("/api/employees/%s", createdEmployee.getEmpNo()));
        return ResponseEntity.created(location).body(createdEmployee);
    }

    // 교직원 정보 수정 (관리자용)
    @PutMapping("/{empNo}/admin-info")
    public ResponseEntity<EmployeeDto> adminUpdateEmployeeInfo(@PathVariable String empNo, @RequestBody AdminUpdateEmployeeDto dto) {
        // 관리자용 교직원 정보 수정 처리
        EmployeeDto updatedEmployee = employeeService.adminUpdateEmployeeInfo(empNo, dto);

        // 수정된 교직원 정보를 반환
        return ResponseEntity.ok(updatedEmployee);
    }

    // 교직원 정보 수정 (개인용)
    @PutMapping("/{empNo}/my-info")
    public ResponseEntity<EmployeeDto> updateMyInfo(@PathVariable String empNo, @RequestBody EmployeeUpdateMyInfoDto dto) {
        // 개인용 교직원 정보 수정 처리
        EmployeeDto updatedEmployee = employeeService.updateMyInfo(empNo, dto);

        // 수정된 교직원 정보를 반환
        return ResponseEntity.ok(updatedEmployee);
    }

    // 교직원 검색 및 페이징 처리
    @GetMapping
    public ResponseEntity<Page<EmployeeDto>> searchEmployees(
            @ModelAttribute EmployeeSearchDto searchDto,
            Pageable pageable) {
        // 교직원 검색 및 페이징 처리
        Page<EmployeeDto> employeePage = employeeService.getEmployeePage(searchDto, pageable);

        // 검색된 교직원 정보를 반환
        return ResponseEntity.ok(employeePage);
    }

    // 교직원 단건 조회
    @GetMapping("/{empNo}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable String empNo) {
        // 교직원 단건 조회
        EmployeeDto employeeDto = employeeService.getEmployee(empNo);

        // 조회된 교직원 정보를 반환
        return ResponseEntity.ok(employeeDto);
    }

    // 교직원 상태 변경
    @PatchMapping("/{empNo}/status")
    public ResponseEntity<EmployeeDto> changeEmployeeStatus(
            @PathVariable String empNo,
            @Valid @RequestBody StatusChangeRequest request) {
        // 교직원 상태 변경 처리
        EmployeeDto updatedEmployee = employeeService.changeStatus(empNo, request.getStatusCode());

        // 상태 변경된 교직원 정보를 반환
        return ResponseEntity.ok(updatedEmployee);
    }
}
