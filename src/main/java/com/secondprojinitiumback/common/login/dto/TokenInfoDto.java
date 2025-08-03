package com.secondprojinitiumback.common.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenInfoDto {
    private String grantType;           // 토큰 타입 (e.g., "Bearer")
    private String accessToken;         // 액세스 토큰
    private String refreshToken;        // 리프래쉬 토큰
    private Long accessTokenExpiresIn;  // 액세스 토큰 만료시각
    private Long refreshTokenExpiresIn; // 리프래쉬 토큰 만료시각
}