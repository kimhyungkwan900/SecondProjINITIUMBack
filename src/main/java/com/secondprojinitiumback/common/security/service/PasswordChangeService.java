package com.secondprojinitiumback.common.security.service;

import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.security.Repository.LoginInfoRepository;
import com.secondprojinitiumback.common.security.Repository.PasswordHistoryRepository;
import com.secondprojinitiumback.common.security.config.PasswordPolicyConfig;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.domain.PasswordHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordChangeService {

    private final LoginInfoRepository loginInfoRepository;
    private final PasswordHistoryRepository passwordHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicyConfig passwordPolicyConfig;

    @Transactional
    public void changePassword(String loginId, String currentPassword, String newPassword) {
        LoginInfo loginInfo = loginInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(currentPassword, loginInfo.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 새 비밀번호 정책 검증
        validatePasswordPolicy(newPassword);

        // 이전 비밀번호와 중복 검증
        validatePasswordHistory(loginId, newPassword);

        // 현재 비밀번호를 히스토리에 저장
        savePasswordHistory(loginId, loginInfo.getPassword());

        // 새 비밀번호로 변경
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        loginInfo.changePassword(encodedNewPassword);

        // 오래된 히스토리 정리
        cleanupOldPasswordHistory(loginId);

        log.info("Password changed successfully for loginId: {}", loginId);
    }

    @Transactional
    public void changePasswordByAdmin(String loginId, String newPassword) {
        LoginInfo loginInfo = loginInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        // 새 비밀번호 정책 검증
        validatePasswordPolicy(newPassword);

        // 이전 비밀번호와 중복 검증
        validatePasswordHistory(loginId, newPassword);

        // 현재 비밀번호를 히스토리에 저장
        savePasswordHistory(loginId, loginInfo.getPassword());

        // 새 비밀번호로 변경 (관리자가 변경할 경우 변경 필요 상태로 설정)
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        loginInfo.issueTemporaryPassword(encodedNewPassword);

        // 오래된 히스토리 정리
        cleanupOldPasswordHistory(loginId);

        log.info("Password changed by admin for loginId: {}", loginId);
    }

    private void validatePasswordPolicy(String password) {
        if (password.length() < passwordPolicyConfig.getPasswordMinLength()) {
            throw new CustomException(ErrorCode.PASSWORD_TOO_SHORT);
        }

        if (passwordPolicyConfig.isRequireNumber() && !Pattern.matches(".*\\d.*", password)) {
            throw new CustomException(ErrorCode.PASSWORD_MISSING_NUMBER);
        }

        if (passwordPolicyConfig.isRequireUppercase() && !Pattern.matches(".*[A-Z].*", password)) {
            throw new CustomException(ErrorCode.PASSWORD_MISSING_UPPERCASE);
        }

        if (passwordPolicyConfig.isRequireSpecialCharacter() && 
            !Pattern.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", password)) {
            throw new CustomException(ErrorCode.PASSWORD_MISSING_SPECIAL);
        }
    }

    private void validatePasswordHistory(String loginId, String newPassword) {
        List<PasswordHistory> recentPasswords = passwordHistoryRepository
                .findRecentPasswordHistory(loginId, passwordPolicyConfig.getPasswordHistoryCount());

        for (PasswordHistory history : recentPasswords) {
            if (passwordEncoder.matches(newPassword, history.getEncryptedPassword())) {
                throw new CustomException(ErrorCode.PASSWORD_RECENTLY_USED);
            }
        }

        // 현재 비밀번호와도 비교
        LoginInfo loginInfo = loginInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));
        
        if (passwordEncoder.matches(newPassword, loginInfo.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_SAME_AS_CURRENT);
        }
    }

    private void savePasswordHistory(String loginId, String encryptedPassword) {
        PasswordHistory passwordHistory = PasswordHistory.create(loginId, encryptedPassword);
        passwordHistoryRepository.save(passwordHistory);
    }

    private void cleanupOldPasswordHistory(String loginId) {
        long historyCount = passwordHistoryRepository.countByLoginId(loginId);
        if (historyCount > passwordPolicyConfig.getPasswordHistoryCount()) {
            passwordHistoryRepository.deleteOldPasswordHistory(loginId, passwordPolicyConfig.getPasswordHistoryCount());
        }
    }
}