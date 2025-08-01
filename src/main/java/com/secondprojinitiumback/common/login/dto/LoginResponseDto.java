package com.secondprojinitiumback.common.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private TokenInfoDto tokenInfo;
    private UserDetailDto userDetail;
}
