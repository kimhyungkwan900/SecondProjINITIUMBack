package com.secondprojinitiumback.common.security.service.Impl;

import com.secondprojinitiumback.common.security.constant.Role;
import com.secondprojinitiumback.common.security.domain.LoginAuthInfo;
import com.secondprojinitiumback.common.security.domain.LoginHistory;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.dto.Request.CreateLoginDto;
import com.secondprojinitiumback.common.security.dto.Response.TokenInfoDto;
import com.secondprojinitiumback.common.security.dto.Response.UserDetailDto;
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

    private static final String PWD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$%*#?&])[A-Za-z\\d$@$%*#?&]{8,}$";

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
    @Transactional
    @Override
    public void changePassword(String loginId, String currentPassword, String newPassword) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, loginInfo.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 비밀번호 유효성검사
        if (newPassword == null || !newPassword.matches(PWD_REGEX)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD_FORMAT);
        }

        // 기존 비밀번호와 동일하다면 예외
        if (passwordEncoder.matches(newPassword, loginInfo.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        // 새 비밀번호 암호화 후 변경
        String encoded = passwordEncoder.encode(newPassword);
        loginInfo.changePassword(encoded);

        loginInfoRepository.save(loginInfo);
    }

    // 로그인정보 삭제
    @Override
    public void deleteLoginInfo(String loginId) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));
        loginInfoRepository.delete(loginInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public void verifyCurrentPassword(String loginId, String currentPassword) {
        // 로그인 ID로 사용자 정보를 조회
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        // 입력된 비밀번호와 DB에 저장된 암호화된 비밀번호를 비교
        if (!passwordEncoder.matches(currentPassword, loginInfo.getPassword())) {
            //일치하지 않으면 예외를 발생
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    @Override
    public LoginInfo authenticate(String loginId, String rawPassword) {
        LoginInfo loginInfo = loginInfoRepository.findById(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 계정 상태 확인 (잠금 or 비활성화 시 로그인 불가)
        if ("L".equals(loginInfo.getAccountStatusCode())) {
            throw new CustomException(ErrorCode.ACCOUNT_LOCKED);
        }
        if ("D".equals(loginInfo.getAccountStatusCode())) {
            throw new CustomException(ErrorCode.ACCOUNT_DISABLED);
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(rawPassword, loginInfo.getPassword())) {
            loginInfo.increaseLoginFailCount();

            if (loginInfo.getLoginFailCount() >= 5) {
                loginInfo.lockAccount(); // 잠금 처리
            }

            loginInfoRepository.save(loginInfo);
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 로그인 성공 시 실패 횟수 초기화
        if (loginInfo.getLoginFailCount() > 0) {
            loginInfo.resetLoginFailCount();
            loginInfoRepository.save(loginInfo);
        }

        return loginInfo;
    }


    @Override
    @Transactional
    public void saveUserAuthInfo(LoginInfo loginInfo, TokenInfoDto tokenInfo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime refreshExp = now.plusSeconds(
                tokenInfo.getRefreshTokenExpiresIn()
        );
        // 새 인증 정보 발급(AT/RT) 및 발급·만료·사용시각 기록
        LoginAuthInfo authInfo = LoginAuthInfo.issue(
                loginInfo,
                tokenInfo.getAccessToken(),
                tokenInfo.getRefreshToken(),
                now,
                refreshExp
        );
        authInfo.markUsed(now);
        loginAuthInfoRepository.save(authInfo);
    }

    @Override
    @Transactional
    public void saveLoginHistory(LoginInfo loginInfo, String ipAddress) {
        // 성공 로그인 이력
        LoginHistory history = LoginHistory.success(loginInfo, ipAddress);
        loginHistoryRepository.save(history);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailDto loadUserDetail(LoginInfo loginInfo) {
        String userType = loginInfo.getUserType();
        String loginId = loginInfo.getLoginId();

        // 사용자 유형에 따라 학생 또는 직원 정보 조회
        if (Role.STUDENT.getUserType().equalsIgnoreCase(userType)) {
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
                    .passwordChangeRequired(loginInfo.getPasswordChangeRequired())
                    .loginFailCount(loginInfo.getLoginFailCount())
                    .build();
        } else if (Role.EMPLOYEE.getUserType().equalsIgnoreCase(userType) || Role.ADMIN.getUserType().equalsIgnoreCase(userType)) {
            Employee employee = employeeRepository.findByLoginInfoLoginId(loginId)
                    .orElseThrow(() -> new CustomException(ErrorCode.EMPLOYEE_NOT_FOUND));
            return UserDetailDto.builder()
                    .userType(userType)
                    .name(employee.getName())
                    .email(employee.getEmail())
                    .empNo(employee.getEmpNo())
                    .schoolSubject(employee.getSchoolSubject().getSubjectName())
                    .loginId(loginInfo.getLoginId())
                    .gender(employee.getGender().getId().getCode())
                    .grade(null)
                    .passwordChangeRequired(loginInfo.getPasswordChangeRequired())
                    .loginFailCount(loginInfo.getLoginFailCount())
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

    @Override
    @Transactional
    public void logout(String accessToken, String refreshToken) {
        // 액세스 토큰으로 인증 정보 조회 및 삭제
        loginAuthInfoRepository.findByAccessToken(accessToken)
                .ifPresent(loginAuthInfoRepository::delete);

        // 리프레시 토큰으로 인증 정보 조회 및 삭제
        loginAuthInfoRepository.findByRefreshToken(refreshToken)
                .ifPresent(loginAuthInfoRepository::delete);
    }
}