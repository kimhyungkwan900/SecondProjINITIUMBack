package com.secondprojinitiumback.common.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailDto {
    private String loginId;
    private String userType;
    private String name;
    private String email;
    private String studentNo;
    private String employeeNo;
    private String schoolSubject;
}
