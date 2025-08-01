package com.secondprojinitiumback.common.login.service.serviceImpl;

import com.secondprojinitiumback.common.login.domain.LoginInfo;
import com.secondprojinitiumback.common.login.dto.CreateLoginDto;
import com.secondprojinitiumback.common.login.repository.LoginInfoRepository;
import com.secondprojinitiumback.common.login.service.serviceInterface.LoginInfoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginInfoServiceImpl implements LoginInfoService {


    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginInfoRepository loginInfoRepository;

    @Override
    public LoginInfo createLoginInfo(CreateLoginDto createLoginDto) {
        // 로그인 ID 중복 체크
        String rawPassWord = createLoginDto.getBirthDate().format(DateTimeFormatter.BASIC_ISO_DATE);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassWord);

        // 로그인 정보 생성
        LoginInfo loginInfo = LoginInfo.builder()
                .loginId(createLoginDto.getLoginId())
                .password(encodedPassword)
                .userType(createLoginDto.getUserType())
                .passwordChangeRequired(true)
                .build();

        // 로그인 정보 저장
        return loginInfoRepository.save(loginInfo);
    }

    // 비밀번호 변경 (암호화 + 저장)
    @Override
    public void changePassword(String loginId, String currentPassword, String newPassword) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("로그인 정보 없음: " + loginId));

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(currentPassword, loginInfo.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호 암호화
        String newEncodedPassword = passwordEncoder.encode(newPassword);
        // 비밀번호 변경
        loginInfo.changePassword(newEncodedPassword);
    }

    @Override
    public void deleteLoginInfo(String loginId) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("로그인 정보 없음: " + loginId));
        loginInfoRepository.delete(loginInfo);
    }

    // 비밀번호 일치 여부 확인 (현재 미사용)
    @Override
    public boolean matchesRawPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
