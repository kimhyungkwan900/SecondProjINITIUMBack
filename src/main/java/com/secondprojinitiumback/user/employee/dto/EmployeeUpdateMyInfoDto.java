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
public class EmployeeUpdateMyInfoDto {
    private String email;
    private String tel;
    private String bankAccountNo;
    private LocalDate birthDate;
}