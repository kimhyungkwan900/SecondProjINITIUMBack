package com.secondprojinitiumback.common.security.service.Impl;

import com.secondprojinitiumback.common.security.domain.LoginAuthInfo;
import com.secondprojinitiumback.common.security.domain.LoginHistory;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.dto.CreateLoginDto;
import com.secondprojinitiumback.common.security.dto.TokenInfoDto;
import com.secondprojinitiumback.common.security.dto.UserDetailDto;
import com.secondprojinitiumback.common.security.Repository.LoginAuthInfoRepository;
import com.secondprojinitiumback.common.security.Repository.LoginHistoryRepository;
import com.secondprojinitiumback.common.security.Repository.LoginInfoRepository;
import com.secondprojinitiumback.common.security.service.LoginInfoService;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginInfoServiceImpl implements LoginInfoService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final LoginInfoRepository loginInfoRepository;
    private final LoginAuthInfoRepository loginAuthInfoRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;

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
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(currentPassword, loginInfo.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 새 비밀번호 유효성 검사
        if (newPassword.length() < 8) {
            throw new CustomException(ErrorCode.PASSWORD_TOO_SHORT);
        }

        // 새 비밀번호 암호화
        String newEncodedPassword = passwordEncoder.encode(newPassword);
        // 비밀번호 변경
        loginInfo.changePassword(newEncodedPassword);

        // 비밀번호 변경일자 업데이트
        loginInfo.updateLastPasswordChangeDateTime();
        loginInfoRepository.save(loginInfo);
    }

    // 로그인정보 삭제
    @Override
    public void deleteLoginInfo(String loginId) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));
        loginInfoRepository.delete(loginInfo);
    }

    // 비밀번호 일치 여부 확인 (현재 미사용)
    @Override
    public boolean matchesRawPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public LoginInfo authenticate(String loginId, String rawPassword) {
        // 로그인 ID로 사용자 정보 조회
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        
        // 비밀번호 검증
        if (!passwordEncoder.matches(rawPassword, loginInfo.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 로그인 정보 반환
        return loginInfo;
    }

    @Override
    @Transactional
    public void saveUserAuthInfo(LoginInfo loginInfo, TokenInfoDto tokenInfo) {
        LocalDateTime now = LocalDateTime.now();

        LoginAuthInfo authInfo = LoginAuthInfo.builder()
                .loginInfo(loginInfo)
                .accessToken(tokenInfo.getAccessToken())
                .refreshToken(tokenInfo.getRefreshToken())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(tokenInfo.getAccessTokenExpiresIn()))
                .lastUsedAt(now)
                .isForcedLogout(false)
                .build();
        loginAuthInfoRepository.save(authInfo);
    }

    @Override
    @Transactional
    public void saveLoginHistory(LoginInfo loginInfo, String ipAddress) {
        // 로그인 이력 저장
        LoginHistory history = LoginHistory.builder()
                .loginInfo(loginInfo)
                .loginIpAddress(ipAddress)
                .loginDateTime(LocalDateTime.now())
                .build();
        loginHistoryRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDto loadUserDetail(LoginInfo loginInfo) {
        String userType = loginInfo.getUserType();
        String loginId = loginInfo.getLoginId();

        // 사용자 유형에 따라 학생 또는 직원 정보 조회
        if ("S".equalsIgnoreCase(userType)) {
            Student student = studentRepository.findByLoginInfoLoginId(loginId)
                    .orElseThrow(() -> new CustomException(ErrorCode.STUDENT_NOT_FOUND));
            return UserDetailDto.builder()
                    .userType(userType)
                    .name(student.getName())
                    .email(student.getEmail())
                    .studentNo(student.getStudentNo())
                    .schoolSubject(student.getSchoolSubject().getSubjectName())
                    .loginId(loginInfo.getLoginId())
                    .gender(student.getGender().getId().getCode())
                    .grade(student.getGrade())
                    .build();
        } else if ("E".equalsIgnoreCase(userType) || "A".equalsIgnoreCase(userType)) {
            Employee employee = employeeRepository.findByLoginInfoLoginId(loginId)
                    .orElseThrow(() -> new CustomException(ErrorCode.EMPLOYEE_NOT_FOUND));
            return UserDetailDto.builder()
                    .userType(userType)
                    .name(employee.getName())
                    .email(employee.getEmail())
                    .employeeNo(employee.getEmpNo())
                    .schoolSubject(employee.getSchoolSubject().getSubjectName())
                    .loginId(loginInfo.getLoginId())
                    .gender(employee.getGender().getId().getCode())
                    .grade(null)
                    .build();
        } else {
            throw new CustomException(ErrorCode.UNKNOWN_USER_TYPE);
        }
    }

    @Override
    public LoginInfo getLoginInfoByLoginId(String loginId) {
        // 로그인 ID로 로그인 정보 조회
        return loginInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
