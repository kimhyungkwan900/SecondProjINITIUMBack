package com.secondprojinitiumback.common.security.dto.Response;

import com.secondprojinitiumback.common.security.dto.Response.UserDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private UserDetailDto userInfo;
}
