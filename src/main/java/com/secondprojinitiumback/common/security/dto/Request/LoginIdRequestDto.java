package com.secondprojinitiumback.common.security.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginIdRequestDto {
    @NotBlank
    private String loginId;
}