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
        String rawPassWord = createLoginDto.getBirthDate().format(DateTimeFormatter.BASIC_ISO_DATE);
        String encodedPassword = passwordEncoder.encode(rawPassWord);

        LoginInfo loginInfo = LoginInfo.builder()
                .loginId(createLoginDto.getLoginId())
                .password(encodedPassword)
                .userType(createLoginDto.getUserType())
                .passwordChangeRequired(true)
                .build();

        return loginInfoRepository.save(loginInfo);
    }

    // 비밀번호 변경 (암호화 + 저장)
    @Override
    public void changePassword(String loginId, String currentPassword, String newPassword) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("로그인 정보 없음: " + loginId));

        if (!passwordEncoder.matches(currentPassword, loginInfo.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        String newEncodedPassword = passwordEncoder.encode(newPassword);
        loginInfo.changePassword(newEncodedPassword);
    }

    @Override
    public void deleteLoginInfo(String loginId) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("로그인 정보 없음: " + loginId));
        loginInfoRepository.delete(loginInfo);
    }

    // 비밀번호 검증
    public boolean matchesRawPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
