package com.secondprojinitiumback.common.security.controller;

import com.secondprojinitiumback.common.security.dto.Request.EmailRequestDto;
import com.secondprojinitiumback.common.security.dto.Request.EmailVerifyRequestDto;
import com.secondprojinitiumback.common.security.dto.Request.LoginIdRequestDto;
import com.secondprojinitiumback.common.security.dto.Request.ResetPasswordRequestDto;
import com.secondprojinitiumback.common.security.dto.Response.EmailResponseDto;
import com.secondprojinitiumback.common.security.service.Impl.AccountRecoverService;
import com.secondprojinitiumback.common.security.service.EmailAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AccountRecoverController {

    private final AccountRecoverService accountRecoverService;
    private final EmailAuthService emailAuthService;

    @PostMapping("/send-email-code")
    public ResponseEntity<Void> sendEmailAuthCode(@Valid @RequestBody EmailRequestDto requestDto) {
        emailAuthService.sendAuthCode(requestDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-email-code")
    public ResponseEntity<Boolean> verifyEmailAuthCode(@RequestBody EmailVerifyRequestDto requestDto) {
        boolean isVerified = emailAuthService.checkAuthCode(requestDto.getEmail(), requestDto.getAuthCode());
        return ResponseEntity.ok(isVerified);
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findLoginId(@Valid @RequestBody EmailRequestDto requestDto) {
        String loginId = accountRecoverService.findLoginIdByEmail(requestDto.getEmail());
        return ResponseEntity.ok(Collections.singletonMap("loginId", loginId));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        accountRecoverService.issueTemporaryPassword(requestDto.getLoginId());
        return ResponseEntity.ok(Collections.singletonMap("message", "임시 비밀번호가 이메일로 발송되었습니다."));
    }

    // loginId → 이메일 조회
    @PostMapping("/user-email")
    public ResponseEntity<EmailResponseDto> findUserEmailByLoginId(@Valid @RequestBody LoginIdRequestDto req) {
        String email = accountRecoverService.getEmailByLoginId(req.getLoginId());
        return ResponseEntity.ok(
                EmailResponseDto.builder()
                        .email(email)
                        .maskedEmail(maskEmail(email))
                        .build()
        );
    }

    // 한 번에 전송까지 처리하는 엔드포인트
    @PostMapping("/send-email-code-by-loginId")
    public ResponseEntity<EmailResponseDto> sendEmailCodeByLoginId(@Valid @RequestBody LoginIdRequestDto req) {
        String email = accountRecoverService.getEmailByLoginId(req.getLoginId());
        emailAuthService.sendAuthCode(email);
        return ResponseEntity.ok(
                EmailResponseDto.builder()
                        .email(null) // 실제 주소는 굳이 다시 안내 안 해도 됨
                        .maskedEmail(maskEmail(email))
                        .build()
        );
    }

    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        String[] parts = email.split("@", 2);
        String local = parts[0], domain = parts[1];
        if (local.length() <= 2) return local.charAt(0) + "*@" + domain;
        String maskedLocal = local.charAt(0) + "*".repeat(local.length() - 2) + local.charAt(local.length() - 1);
        return maskedLocal + "@" + domain;
    }
}
