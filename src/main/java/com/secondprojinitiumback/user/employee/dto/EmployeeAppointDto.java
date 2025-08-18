package com.secondprojinitiumback.user.employee.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class EmployeeAppointDto {
    private String name;            // 이름
    private LocalDate birthDate;    // 생년월일
    private String gender;          // 성별 코드
    private String email;           // 이메일
    private String tel;             // 전화번호
    private String subjectCode;     // 소속 센터, 부서, 학교 등
    private String bankAccountNo;   // 계좌번호
    private String bankCode;        // 은행 코드
    private String employeeStatusCode;  // 교직원 상태 코드
}
