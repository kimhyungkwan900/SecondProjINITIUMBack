package com.secondprojinitiumback.common.login.controller;

import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.common.login.dto.LoginRequestDto;
import com.secondprojinitiumback.common.login.dto.LoginResponseDto;
import com.secondprojinitiumback.common.login.dto.TokenInfoDto;
import com.secondprojinitiumback.common.login.dto.UserDetailDto;
import com.secondprojinitiumback.common.login.service.serviceInterface.LoginInfoService;
import com.secondprojinitiumback.common.security.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogInController {

    private final LoginInfoService loginInfoService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        // ID/PW 및 계정 상태 확인
        LoginInfo loginInfo = loginInfoService.authenticate(loginRequestDto.getLoginId(), loginRequestDto.getPassword());

        if (!"N".equals(loginInfo.getAccountStatusCode())) {
            throw new RuntimeException(new AccountLockedException("계정이 잠겨있습니다."));
        }

        // Access Token 및 Refresh Token 발급
        TokenInfoDto tokenInfo = tokenProvider.generateTokens(loginInfo.getLoginId(), loginInfo.getUserType());

        // 로그인 정보 및 이력 저장
        loginInfoService.saveUserAuthInfo(loginInfo, tokenInfo);
        loginInfoService.saveLoginHistory(loginInfo, loginRequestDto.getIpAddress());

        // 사용자 정보 조회
        UserDetailDto userDetail = loginInfoService.loadUserDetail(loginInfo);

        // 응답 생성
        LoginResponseDto response = new LoginResponseDto(tokenInfo, userDetail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<UserDetailDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // 현재 로그인한 사용자 정보 조회
        if (userDetails == null) {
            return ResponseEntity.notFound().build();
        }
        // UserDetails에서 로그인 ID를 가져와서 LoginInfo 조회
        LoginInfo loginInfo = loginInfoService.getLoginInfoByLoginId(userDetails.getUsername());
        UserDetailDto userDetailDto = loginInfoService.loadUserDetail(loginInfo);

        return ResponseEntity.ok(userDetailDto);
    }
}