package com.secondprojinitiumback.common.security.service.Impl;

import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.security.Repository.EmailAuthRepository;
import com.secondprojinitiumback.common.security.domain.EmailAuth;
import com.secondprojinitiumback.common.security.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;
    private final JavaMailSender mailSender;

    private static final String AUTH_CODE_EMAIL_TITLE = "INITIUM 이메일 인증 코드";

    @Override
    @Transactional
    public void sendAuthCode(String toEmail) {
        // 이미 인증 코드가 존재하는 경우, 기존 코드를 삭제
        String authCode = createAuthCode();
        // 인증 코드의 만료 시간을 설정
        LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);

        // 기존 인증 코드가 있다면 삭제
        EmailAuth emailAuth = EmailAuth.builder()
                .email(toEmail)
                .authCode(authCode)
                .expireDt(expiryDate)
                .isVerified("N")
                .build();

        // 이메일 인증 정보를 저장
        emailAuthRepository.save(emailAuth);
        // 이메일로 인증 코드 전송
        sendEmail(toEmail, AUTH_CODE_EMAIL_TITLE, "인증 코드는 " + authCode + " 입니다.");
    }

    @Override
    @Transactional
    public boolean checkAuthCode(String email, String inputCode) {
        // 이메일 인증 정보를 조회
        EmailAuth emailAuth = emailAuthRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_CODE_NOT_FOUND));

        // 인증 코드가 만료되었는지 확인
        if (emailAuth.isExpired()) {
            throw new CustomException(ErrorCode.AUTH_CODE_EXPIRED);
        }

        // 입력된 인증 코드와 저장된 인증 코드를 비교
        if (!emailAuth.getAuthCode().equals(inputCode)) {
            throw new CustomException(ErrorCode.AUTH_CODE_MISMATCH);
        }

        // 인증 성공 시 isVerified 필드를 'Y'로 업데이트
        EmailAuth verifiedAuth = EmailAuth.builder()
                .email(emailAuth.getEmail())
                .authCode(emailAuth.getAuthCode())
                .expireDt(emailAuth.getExpireDt())
                .isVerified("Y")
                .build();
        emailAuthRepository.save(verifiedAuth);

        return true;
    }

    private String createAuthCode() {
        try {
            // Random 객체 생성 SecureRandom을 사용하여 보안성이 높은 난수 생성
            Random random = SecureRandom.getInstanceStrong();
            int randomNumber = random.nextInt(900000) + 100000; // 100000 ~ 999999
            return String.valueOf(randomNumber);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("안전 AuthCode 생성 불가.", e);
        }
    }

    private void sendEmail(String toEmail, String title, String text) {
        // 이메일 전송을 위한 MimeMessage 생성
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // MimeMessageHelper를 사용하여 이메일 설정
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            // 수신자, 제목, 본문 설정
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(text, true); // true: HTML 형식 사용
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
