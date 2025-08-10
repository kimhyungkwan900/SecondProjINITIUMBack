package com.secondprojinitiumback.common.security.service;


public interface EmailAuthService {
    void sendAuthCode(String email);
    boolean checkAuthCode(String email, String inputCode);
}
