package com.secondprojinitiumback.common.login.service.serviceInterface;

import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.common.login.dto.CreateLoginDto;
import com.secondprojinitiumback.common.login.dto.TokenInfoDto;
import com.secondprojinitiumback.common.login.dto.UserDetailDto;

public interface LoginInfoService {

    // 로그인 정보 생성
    LoginInfo createLoginInfo(CreateLoginDto dto);

    // 비밀번호 변경 (암호화 + 저장)
    void changePassword(String loginId, String currentPassword, String newPassword);

    void deleteLoginInfo(String loginId);

    // 비밀번호 일치 여부 확인 (현재 미사용)
    boolean matchesRawPassword(String rawPassword, String encodedPassword);

    // 로그인 인증
    LoginInfo authenticate(String loginId, String rawPassword);

    // 토큰 정보 저장
    void saveUserAuthInfo(LoginInfo loginInfo, TokenInfoDto tokenInfo);

    // 로그인 이력 저장
    void saveLoginHistory(LoginInfo loginInfo, String ipAddress);

    // 사용자 상세 정보 조회
    UserDetailDto loadUserDetail(LoginInfo loginInfo);

    // 로그인 ID로 로그인 정보 조회
    LoginInfo getLoginInfoByLoginId(String loginId);
}