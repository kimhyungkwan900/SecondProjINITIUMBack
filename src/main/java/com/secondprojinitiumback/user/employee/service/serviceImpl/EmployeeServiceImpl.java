package com.secondprojinitiumback.user.employee.service.serviceImpl;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.common.login.dto.CreateLoginDto;
import com.secondprojinitiumback.common.login.service.serviceInterface.LoginInfoService;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.domain.EmployeeStatusInfo;
import com.secondprojinitiumback.user.employee.dto.*;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import com.secondprojinitiumback.user.employee.repository.EmployeeStatusInfoRepository;
import com.secondprojinitiumback.user.employee.service.serviceinterface.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;
    private final BankAccountRepository bankAccountRepository;
    private final CommonCodeRepository commonCodeRepository;
    private final LoginInfoService loginInfoService;
    private final EmployeeStatusInfoRepository employeeStatusInfoRepository;

    @Override
    public void appointProfessor(ProfessorAppointDto dto) {
        appointEmployee(dto, "P", "E"); // 교수
    }
    @Override
    public void appointInstructor(CounselorHireDto dto) {
        appointEmployee(dto, "K", "E"); // 강사
    }
    @Override
    public void appointStaff(StaffAppointDto dto) {
        appointEmployee(dto, "S", "E"); // 센터직원 등
    }

    @Override
    public void appointEmployee(EmployeeAppointDto dto, String rolePrefix, String userType) {
        SchoolSubject schoolSubject = findSchoolSubjectById(dto.getSchoolSubjectNo());
        String employeeNo = generateEmployeeNo(rolePrefix, schoolSubject.getSubjectCode());
        LoginInfo loginInfo = createLoginInfo(employeeNo, userType, dto.getBirthDate());
        BankAccount bankAccount = findBankAccountByIdNullable(dto.getBankAccountNo());
        CommonCode gender = findGenderByCode(dto.getGender());
        EmployeeStatusInfo employeeStatus = findStatusByCode(dto.getEmployeeStatus());

        Employee employee = Employee.create(
                employeeNo,
                loginInfo,
                schoolSubject,
                bankAccount,
                dto.getName(),
                gender,
                dto.getBirthDate(),
                dto.getEmail(),
                dto.getTel(),
                employeeStatus
        );
        employeeRepository.save(employee);
    }


    @Override
    public void adminUpdateEmployeeInfo(String employeeNo, AdminUpdateEmployeeDto dto) {
        Employee employee = findEmployeeById(employeeNo);
        SchoolSubject schoolSubject = findSchoolSubjectById(dto.getSchoolSubjectNo());
        CommonCode gender = findGenderByCode(dto.getGender());
        EmployeeStatusInfo employeeStatus = findStatusByCode(dto.getEmpStatus());
        employee.adminUpdate(dto, schoolSubject, gender, employeeStatus);
    }

    @Override
    public void updateMyInfo(String employeeNo, EmployeeUpdateMyInfoDto dto) {
        Employee employee = findEmployeeById(employeeNo);
        BankAccount bankAccount = findBankAccountByIdNullable(dto.getBankAccountNo());
        employee.updateMyInfo(dto, bankAccount);
    }
    
    // TODO : dto 변환 메서드 구현할것
    @Override
    public EmployeeSearchDto getEmployee(String employeeNo) {
        Employee employee = findEmployeeById(employeeNo);
        return null;
    }

    @Override
    public List<EmployeeDto> getEmployeeList(EmployeeSearchDto employeeSearchDto) {
        List<Employee> employees = employeeRepository.search(employeeSearchDto);
        return employees.stream().map(this::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public void resignEmployee(String employeeNo) {

        // 교직원 조회
        Employee employee = findEmployeeById(employeeNo);

        // 퇴사상태코드 설정 10 재직 20 휴직 30 퇴사
        EmployeeStatusInfo resignStatus = findStatusByCode("30");

        // employee 상태 변경
        employee.changeStatus(resignStatus);

        LoginInfo loginInfo = employee.getLoginInfo();
        if (loginInfo != null) {
            // 로그인 정보 삭제
            loginInfoService.deleteLoginInfo(loginInfo.getLoginId());
        }

    }

    public Page<EmployeeDto> getEmployeePage(EmployeeSearchDto employeeSearchDto, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.searchPage(employeeSearchDto, pageable);
        return employeePage.map(this::toEmployeeDto);
    }

    // entity 조회 메서드들

    private Employee findEmployeeById(String employeeNo) {
        return employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("직원 정보 없음: " + employeeNo));
    }

    private SchoolSubject findSchoolSubjectById(String subjectNo) {
        return schoolSubjectRepository.findById(subjectNo)
                .orElseThrow(() -> new EntityNotFoundException("부서(학과) 코드 없음: " + subjectNo));
    }

    private BankAccount findBankAccountById(String accountNo) {
        return bankAccountRepository.findById(accountNo)
                .orElseThrow(() -> new EntityNotFoundException("계좌 정보가 없습니다: " + accountNo));
    }

    // 계좌번호는 null/blank 허용
    private BankAccount findBankAccountByIdNullable(String accountNo) {
        if (accountNo == null || accountNo.isBlank()) {
            return null;
        }
        return findBankAccountById(accountNo);
    }

    private CommonCode findGenderByCode(String genderCode) {
        return commonCodeRepository.findByCdAndCdSe(genderCode, "CO0001")
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않은 성별 코드: " + genderCode));
    }

    private EmployeeStatusInfo findStatusByCode(String statusCode) {
        return employeeStatusInfoRepository
                .findByEmployeeStatusCodeAndEmployeeStatusCodeSe(statusCode, "AM0120")
                .orElseThrow(() -> new EntityNotFoundException("상태 없음: " + statusCode));
    }

    private LoginInfo createLoginInfo(String loginId, String userType, LocalDate birthDate) {
        CreateLoginDto createLoginDto = CreateLoginDto.builder()
                .loginId(loginId)
                .userType(userType)
                .birthDate(birthDate)
                .build();
        return loginInfoService.createLoginInfo(createLoginDto);
    }

    // 교직원 번호 생성 로직
    private String generateEmployeeNo(String role, String deptCode) {
        // role: "P" (교수), "H" (직원), "S" (센터직원)
        String prefix = role;

        // 부서 코드 3자리로 변환
        String dept = String.format("%03d", Integer.parseInt(deptCode));

        // 최근 시퀀스 조회
        Optional<String> lastEmpNo = employeeRepository.findTopByEmpNoStartingWithOrderByEmpNoDesc(prefix + dept);

        // 시퀀스 번호 생성
        int seq = 1;
        if (lastEmpNo.isPresent()) {
            String lastSeq = lastEmpNo.get().substring(lastEmpNo.get().length() - 3);
            seq = Integer.parseInt(lastSeq) + 1;
        }

        // 시퀀스 번호를 3자리 문자열로 변환
        String seqStr = String.format("%03d", seq);

        // 최종 교직원 번호 생성
        return prefix + dept + seqStr;
    }

    // Employee 엔티티를 EmployeeSearchDto로 변환
    private EmployeeDto toEmployeeDto(Employee employee) {
        return null;
    }
}
