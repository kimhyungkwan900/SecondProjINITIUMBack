package com.secondprojinitiumback.common.security.controller;

import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.dto.*;
import com.secondprojinitiumback.common.security.service.LoginInfoService;
import com.secondprojinitiumback.common.security.config.jwt.TokenProvider;
import com.secondprojinitiumback.common.security.utils.CookieConstants;
import com.secondprojinitiumback.common.security.utils.CookieUtils;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginInfoService loginInfoService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        // 로그인 인증
        LoginInfo loginInfo = loginInfoService.authenticate(loginRequestDto.getLoginId(), loginRequestDto.getPassword());

        // 계정 상태확인
        if (!"N".equals(loginInfo.getAccountStatusCode())) {
            throw new CustomException(ErrorCode.ACCOUNT_LOCKED);
        }

        // 토큰 발급
        TokenInfoDto tokenInfo = tokenProvider.generateTokens(loginInfo.getLoginId(), loginInfo.getUserType());

        // CookieUtils를 사용하여 쿠키에 토큰 추가
        CookieUtils.addCookie(response, CookieConstants.ACCESS_TOKEN, tokenInfo.getAccessToken(), tokenProvider.getAccessTokenExpirySeconds());
        CookieUtils.addCookie(response, CookieConstants.REFRESH_TOKEN, tokenInfo.getRefreshToken(), tokenProvider.getRefreshTokenExpirySeconds());

        // 로그인 인증 정보 저장
        loginInfoService.saveUserAuthInfo(loginInfo, tokenInfo);
        // 로그인 이력 저장
        String ip = resolveClientIp(request);
        loginInfoService.saveLoginHistory(loginInfo, ip);

        // 사용자 상세 정보 조회
        UserDetailDto userDetail = loginInfoService.loadUserDetail(loginInfo);

        // 로그인 응답 DTO 생성
        LoginResponseDto loginResponse = new LoginResponseDto(userDetail);

        // 로그인 성공 응답 반환
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // 토큰 추출
        String accessToken = CookieUtils.getCookie(request, CookieConstants.ACCESS_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);
        String refreshToken = CookieUtils.getCookie(request, CookieConstants.REFRESH_TOKEN)
                .map(Cookie::getValue)
                .orElse(null);

        // 서버에서 저장된 인증 정보 무효화 처리
        loginInfoService.logout(accessToken, refreshToken);

        // 쿠키 삭제
        CookieUtils.deleteCookie(request, response, CookieConstants.ACCESS_TOKEN);
        CookieUtils.deleteCookie(request, response, CookieConstants.REFRESH_TOKEN);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // 현재 로그인한 사용자 정보 조회
        if (userDetails == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // UserDetails에서 로그인 ID를 가져와서 LoginInfo 조회
        LoginInfo loginInfo = loginInfoService.getLoginInfoByLoginId(userDetails.getUsername());
        UserDetailDto userDetailDto = loginInfoService.loadUserDetail(loginInfo);

        return ResponseEntity.ok(userDetailDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequestDto requestDto
    ) {
        if (userDetails == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        loginInfoService.changePassword(
                userDetails.getUsername(),
                requestDto.getCurrentPassword(),
                requestDto.getNewPassword()
        );
        return ResponseEntity.ok().build();
    }

    private String resolveClientIp(HttpServletRequest req) {
        // 클라이언트 IP 주소를 헤더에서 추출
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "CF-Connecting-IP"};
        for (String h : headers) {
            // 헤더에서 IP 주소를 가져오고, 유효한 경우 반환
            String v = req.getHeader(h);
            // 헤더 값이 null, 비어있거나 "unknown"인 경우는 제외
            if (v != null && !v.isBlank() && !"unknown".equalsIgnoreCase(v)) {
                return v.split(",")[0].trim();
            }
        }
        return req.getRemoteAddr();
    }
}
