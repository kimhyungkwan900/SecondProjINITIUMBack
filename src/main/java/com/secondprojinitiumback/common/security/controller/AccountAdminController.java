package com.secondprojinitiumback.common.security.controller;

import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.security.Repository.LoginInfoRepository;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/accounts")
@RequiredArgsConstructor
public class AccountAdminController {

    private final LoginInfoRepository loginInfoRepository;

    @PatchMapping("/{loginId}/unlock")
    public ResponseEntity<Void> unlockAccount(@PathVariable String loginId) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        loginInfo.resetLoginFailCount();
        loginInfo.unlockAccount(); // 잠금 해제
        loginInfoRepository.save(loginInfo);

        return ResponseEntity.noContent().build();
    }
}