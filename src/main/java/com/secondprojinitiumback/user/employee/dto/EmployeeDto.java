package com.secondprojinitiumback.user.employee.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EmployeeDto {
    private String empNo;                // 교직원 번호(사번)
    private String subjectCode;          // 소속 학교/학과
    private String employeeStatusCode;   // 상태 코드
    private String bankAccountNo;        // 은행 계좌번호
    private String bankName;             // 은행명
    private String name;                 // 이름
    private String genderCode;           // 성별 코드
    private LocalDate birthDate;         // 생년월일
    private String email;                // 이메일
    private String tel;                  // 전화번호
}