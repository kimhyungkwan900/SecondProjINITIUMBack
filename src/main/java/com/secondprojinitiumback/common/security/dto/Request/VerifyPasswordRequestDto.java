package com.secondprojinitiumback.common.security.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VerifyPasswordRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}