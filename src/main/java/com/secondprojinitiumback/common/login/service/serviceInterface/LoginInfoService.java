package com.secondprojinitiumback.common.login.service.serviceInterface;

import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.common.login.dto.CreateLoginDto;

import java.time.LocalDate;

public interface LoginInfoService {

    // 로그인 정보 생성
    LoginInfo createLoginInfo(CreateLoginDto dto);

    // 비밀번호 변경 (암호화 + 저장)
    void changePassword(String loginId, String currentPassword, String newPassword);

    void deleteLoginInfo(String loginId);

    // 비밀번호 검증
    boolean matchesRawPassword(String rawPassword, String encodedPassword);
}
