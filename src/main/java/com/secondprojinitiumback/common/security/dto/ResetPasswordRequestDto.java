package com.secondprojinitiumback.common.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetPasswordRequestDto {
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    private final String loginId;
}