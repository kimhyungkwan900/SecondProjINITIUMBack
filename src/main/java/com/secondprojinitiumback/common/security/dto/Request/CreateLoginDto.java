package com.secondprojinitiumback.common.security.dto.Request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CreateLoginDto {
    private String loginId;
    private String userType;
    private LocalDate birthDate;
}
