package com.secondprojinitiumback.user.employee.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeSearchDto {
    private String empNo;               // 교직원 번호
    private String name;                // 이름
    private String subjectCode;         // 담당 과목
    private String employeeStatusCode;  // 상태 코드
    private String genderCode;          // 성별 코드
    private String email;               // 이메일
    private String tel;                 // 전화번호
}
