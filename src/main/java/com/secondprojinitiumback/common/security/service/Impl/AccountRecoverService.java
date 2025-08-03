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
        return studentRepository.findByEmail(email)
                .map(student -> student.getLoginInfo().getLoginId())
                .or(() -> employeeRepository.findByEmail(email).map(employee -> employee.getLoginInfo().getLoginId()))
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
}


