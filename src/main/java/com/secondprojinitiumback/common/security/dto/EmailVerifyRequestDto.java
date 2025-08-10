package com.secondprojinitiumback.common.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailVerifyRequestDto {
    private String email;
    private String authCode;
}
