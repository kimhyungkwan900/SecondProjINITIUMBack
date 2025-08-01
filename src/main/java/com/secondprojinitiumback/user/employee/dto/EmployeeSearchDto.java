package com.secondprojinitiumback.user.employee.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class EmployeeSearchDto {
    private String employeeNo;      // 교직원 번호
    private String name;            // 이름
    private String schoolSubject;   // 담당 과목
    private String statusCode;      // 상태 코드
}
