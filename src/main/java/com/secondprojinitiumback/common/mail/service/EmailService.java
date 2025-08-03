package com.secondprojinitiumback.common.mail.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
