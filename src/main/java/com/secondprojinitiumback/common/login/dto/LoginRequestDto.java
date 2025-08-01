package com.secondprojinitiumback.common.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String loginId;
    private String password;
    private String ipAddress;
}
