package com.secondprojinitiumback.common.security.controller;

import com.secondprojinitiumback.common.security.dto.EmailRequestDto;
import com.secondprojinitiumback.common.security.dto.EmailVerifyRequestDto;
import com.secondprojinitiumback.common.security.dto.ResetPasswordRequestDto;
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
        // 이메일 인증 코드 전송
        emailAuthService.sendAuthCode(requestDto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-email-code")
    public ResponseEntity<Boolean> verifyEmailAuthCode(@RequestBody EmailVerifyRequestDto requestDto) {
        // 이메일 인증 코드 검증
        boolean isVerified = emailAuthService.checkAuthCode(requestDto.getEmail(), requestDto.getAuthCode());
        return ResponseEntity.ok(isVerified);
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findLoginId(@Valid @RequestBody EmailRequestDto requestDto) {
        // 로그인 ID 찾기
        String loginId = accountRecoverService.findLoginIdByEmail(requestDto.getEmail());
        return ResponseEntity.ok(Collections.singletonMap("loginId", loginId));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        // 임시 비밀번호 발급
        accountRecoverService.issueTemporaryPassword(requestDto.getLoginId());
        return ResponseEntity.ok(Collections.singletonMap("message", "임시 비밀번호가 이메일로 발송되었습니다."));
    }
}
