package com.secondprojinitiumback.common.security.service.Impl;

import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.mail.service.EmailService;
import com.secondprojinitiumback.common.security.domain.LoginInfo;
import com.secondprojinitiumback.common.security.Repository.LoginInfoRepository;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountRecoverService {

    private final StudentRepository studentRepository;
    private final EmployeeRepository employeeRepository;
    private final LoginInfoRepository loginInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional(readOnly = true)
    public String findLoginIdByEmail(String email) {
        // 이메일로 로그인 ID 찾기
        return studentRepository.findByEmail(email)
                // 학생목록에서 로그인ID 찾기
                .map(student -> student.getLoginInfo().getLoginId())
                // 직원목록에서 로그인ID 찾기
                .or(() -> employeeRepository.findByEmail(email).map(employee -> employee.getLoginInfo().getLoginId()))
                // 없다면 예외 발생
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public void issueTemporaryPassword(String loginId) {
        LoginInfo loginInfo = loginInfoRepository.findByLoginId(loginId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_INFO_NOT_FOUND));

        String temporaryPassword = UUID.randomUUID().toString().substring(0, 8);
        String encodedPassword = passwordEncoder.encode(temporaryPassword);

        loginInfo.issueTemporaryPassword(encodedPassword);

        String email = findEmailByLoginId(loginId);
        emailService.sendSimpleMessage(email, "INITIUM 임시 비밀번호 발급", "임시 비밀번호: " + temporaryPassword);
    }

    public String findEmailByLoginId(String loginId) {
        return studentRepository.findByLoginInfoLoginId(loginId)
                .map(Student::getEmail)
                .or(() -> employeeRepository.findByLoginInfoLoginId(loginId).map(Employee::getEmail))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public String getEmailByLoginId(String loginId) {
        return findEmailByLoginId(loginId);
    }
}


