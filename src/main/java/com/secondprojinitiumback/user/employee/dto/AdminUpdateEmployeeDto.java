package com.secondprojinitiumback.user.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateEmployeeDto {
    private String name;                // 이름
    private LocalDate birthDate;        // 생년월일
    private String gender;              // 성별 코드
    private String email;               // 이메일
    private String tel;                 // 전화번호
    private String empStatus;           // 교직원 상태 코드
    private String subjectCode;     // 소속 센터, 부서, 학교 등
}