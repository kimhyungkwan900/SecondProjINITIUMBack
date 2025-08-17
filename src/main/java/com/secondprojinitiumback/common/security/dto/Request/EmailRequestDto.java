package com.secondprojinitiumback.common.security.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailRequestDto {
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private final String email;
    
    private final String loginId;
}