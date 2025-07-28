package com.secondprojinitiumback.common.security.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class jwtProperties {
    private String jwtSecret;
    private String jwtIssuer;
}
