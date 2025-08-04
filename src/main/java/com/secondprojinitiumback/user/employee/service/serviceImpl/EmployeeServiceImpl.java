package com.secondprojinitiumback.user.employee.service.serviceImpl;

import com.secondprojinitiumback.common.bank.domain.BankAccount;
import com.secondprojinitiumback.common.bank.Repository.BankAccountRepository;
import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.dto.CreateLoginDto;
import com.secondprojinitiumback.common.security.service.LoginInfoService;
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
    public EmployeeDto appointProfessor(ProfessorAppointDto dto) {
        return appointEmployee(dto, "P", "E");
    }

    @Override
    public EmployeeDto appointInstructor(CounselorHireDto dto) {
        return appointEmployee(dto, "K", "E");
    }

    @Override
    public EmployeeDto appointStaff(StaffAppointDto dto) {
        return appointEmployee(dto, "S", "E");
    }

    private EmployeeDto appointEmployee(EmployeeAppointDto dto, String rolePrefix, String userType) {
        SchoolSubject schoolSubject = findSchoolSubjectById(dto.getSchoolSubjectNo());
        String employeeNo = generateEmployeeNo(rolePrefix, schoolSubject.getSubjectCode());
        LoginInfo loginInfo = createLoginInfo(employeeNo, userType, dto.getBirthDate());
        BankAccount bankAccount = findBankAccountByIdNullable(dto.getBankAccountNo());
        CommonCode gender = findGenderByCode(dto.getGender());
        EmployeeStatusInfo employeeStatus = findStatusByCode(dto.getEmployeeStatus());

        Employee employee = Employee.create(
                employeeNo, loginInfo, schoolSubject, bankAccount, dto.getName(),
                gender, dto.getBirthDate(), dto.getEmail(), dto.getTel(), employeeStatus
        );
        Employee savedEmployee = employeeRepository.save(employee);
        return toEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto adminUpdateEmployeeInfo(String employeeNo, AdminUpdateEmployeeDto dto) {
        Employee employee = findEmployeeById(employeeNo);
        SchoolSubject schoolSubject = findSchoolSubjectById(dto.getSchoolSubjectNo());
        CommonCode gender = findGenderByCode(dto.getGender());
        EmployeeStatusInfo employeeStatus = findStatusByCode(dto.getEmpStatus());
        employee.adminUpdate(dto, schoolSubject, gender, employeeStatus);
        return toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateMyInfo(String employeeNo, EmployeeUpdateMyInfoDto dto) {
        Employee employee = findEmployeeById(employeeNo);
        BankAccount bankAccount = findBankAccountByIdNullable(dto.getBankAccountNo());
        employee.updateMyInfo(dto, bankAccount);
        return toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto changeStatus(String employeeNo, String statusCode) {
        Employee employee = findEmployeeById(employeeNo);
        EmployeeStatusInfo newStatus = findStatusByCode(statusCode);
        employee.changeStatus(newStatus);
        return toEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto getEmployee(String employeeNo) {
        Employee employee = findEmployeeById(employeeNo);
        return toEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getEmployeeList(EmployeeSearchDto employeeSearchDto) {
        List<Employee> employees = employeeRepository.search(employeeSearchDto);
        return employees.stream().map(this::toEmployeeDto).collect(Collectors.toList());
    }

    @Override
    public void resignEmployee(String employeeNo) {
        Employee employee = findEmployeeById(employeeNo);
        EmployeeStatusInfo resignStatus = findStatusByCode("30"); // 퇴사 상태 코드
        employee.changeStatus(resignStatus);

        LoginInfo loginInfo = employee.getLoginInfo();
        if (loginInfo != null) {
            loginInfoService.deleteLoginInfo(loginInfo.getLoginId());
        }
        // void 반환이므로 return 문 제거
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeDto> getEmployeePage(EmployeeSearchDto employeeSearchDto, Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.searchPage(employeeSearchDto, pageable);
        return employeePage.map(this::toEmployeeDto);
    }

    // Entity 조회 메소드들

    // 교번/사번으로 직원 조회
    private Employee findEmployeeById(String employeeNo) {
        return employeeRepository.findById(employeeNo)
                .orElseThrow(() -> new EntityNotFoundException("직원 정보 없음: " + employeeNo));
    }

    // 학과 코드로 학과 조회
    private SchoolSubject findSchoolSubjectById(String subjectNo) {
        return schoolSubjectRepository.findById(subjectNo)
                .orElseThrow(() -> new EntityNotFoundException("부서(학과) 코드 없음: " + subjectNo));
    }

    // 계좌 번호로 은행 계좌 조회
    private BankAccount findBankAccountById(String accountNo) {
        return bankAccountRepository.findById(accountNo)
                .orElseThrow(() -> new EntityNotFoundException("계좌 정보가 없습니다: " + accountNo));
    }

    // 계좌 번호가 null이거나 비어있을 경우 null 반환
    private BankAccount findBankAccountByIdNullable(String accountNo) {
        if (accountNo == null || accountNo.isBlank()) {
            return null;
        }
        return findBankAccountById(accountNo);
    }

    // 성별 코드로 CommonCode 조회
    private CommonCode findGenderByCode(String genderCode) {
        return commonCodeRepository.findById_CodeAndId_CodeGroup(genderCode, "CO0001")
                .orElseThrow(() -> new EntityNotFoundException("유효하지 않은 성별 코드: " + genderCode));
    }

    // 직원 상태 코드로 상태 정보 조회
    private EmployeeStatusInfo findStatusByCode(String statusCode) {
        return employeeStatusInfoRepository
                .findByEmployeeStatusCodeAndEmployeeStatusCodeSe(statusCode, "AM0120")
                .orElseThrow(() -> new EntityNotFoundException("상태 없음: " + statusCode));
    }

    // 로그인 정보 생성
    private LoginInfo createLoginInfo(String loginId, String userType, LocalDate birthDate) {
        CreateLoginDto createLoginDto = CreateLoginDto.builder()
                .loginId(loginId)
                .userType(userType)
                .birthDate(birthDate)
                .build();
        return loginInfoService.createLoginInfo(createLoginDto);
    }

    // 교번/사번 생성 로직
    private String generateEmployeeNo(String role, String deptCode) {
        String prefix = role;
        String dept = String.format("%03d", Integer.parseInt(deptCode));
        Optional<String> lastEmpNo = employeeRepository.findTopByEmpNoStartingWithOrderByEmpNoDesc(prefix + dept);
        int seq = 1;
        if (lastEmpNo.isPresent()) {
            String lastSeq = lastEmpNo.get().substring(lastEmpNo.get().length() - 3);
            seq = Integer.parseInt(lastSeq) + 1;
        }
        String seqStr = String.format("%03d", seq);
        return prefix + dept + seqStr;
    }

    // Employee 객체를 EmployeeDto로 변환
    private EmployeeDto toEmployeeDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        String schoolSubjectNo = (employee.getSchoolSubject() != null)
                ? employee.getSchoolSubject().getSubjectCode() : null;
        String employeeStatusCode = (employee.getEmployeeStatus() != null)
                ? employee.getEmployeeStatus().getEmployeeStatusCode() : null;
        String genderCode = (employee.getGender() != null)
                ? employee.getGender().getId().getCode() : null;
        String bankAccountNo = null;
        String bankName = null;
        if (employee.getBankAccount() != null) {
            bankAccountNo = employee.getBankAccount().getAccountNo();
            if (employee.getBankAccount().getBankCode() != null) {
                bankName = employee.getBankAccount().getBankCode().getCodeName();
            }
        }
        return EmployeeDto.builder()
                .empNo(employee.getEmpNo())
                .name(employee.getName())
                .email(employee.getEmail())
                .tel(employee.getTel())
                .birthDate(employee.getBirthDate())
                .schoolSubjectNo(schoolSubjectNo)
                .employeeStatusCode(employeeStatusCode)
                .genderCode(genderCode)
                .bankAccountNo(bankAccountNo)
                .bankName(bankName)
                .build();
    }
}