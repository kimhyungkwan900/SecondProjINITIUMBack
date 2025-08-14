package com.secondprojinitiumback.common.security.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class ChangePasswordRequestDto {

    @NotBlank(message = "현재 비밀번호는 필수입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Size(min = 8, message = "새 비밀번호는 8자 이상이어야 합니다.")
    private String newPassword;
}