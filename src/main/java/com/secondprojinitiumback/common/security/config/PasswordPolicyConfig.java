package com.secondprojinitiumback.common.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PasswordPolicyConfig {

    @Value("${security.password.history.count:5}")
    private int passwordHistoryCount;

    @Value("${security.password.expiry.months:6}")
    private int passwordExpiryMonths;

    @Value("${security.password.min.length:8}")
    private int passwordMinLength;

    @Value("${security.password.require.special:true}")
    private boolean requireSpecialCharacter;

    @Value("${security.password.require.number:true}")
    private boolean requireNumber;

    @Value("${security.password.require.uppercase:true}")
    private boolean requireUppercase;
}