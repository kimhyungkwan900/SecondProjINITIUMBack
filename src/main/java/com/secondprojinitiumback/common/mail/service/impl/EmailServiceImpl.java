package com.secondprojinitiumback.common.mail.service.impl;

import com.secondprojinitiumback.common.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        // 간단 메세지 오브젝트 생성
        SimpleMailMessage message = new SimpleMailMessage();
        // 받는사람
        message.setTo(to);
        // 제목
        message.setSubject(subject);
        // 내용
        message.setText(text);
        emailSender.send(message);
    }
}
