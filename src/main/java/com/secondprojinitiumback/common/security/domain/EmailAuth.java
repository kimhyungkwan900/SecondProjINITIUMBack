package com.secondprojinitiumback.common.security.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMAIL_AUTH")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAuth {

    @Id
    @Column(name = "EMAIL", length = 320, nullable = false)
    private String email;

    @Column(name = "AUTH_CODE", length = 6, nullable = false)
    private String authCode;

    @Column(name = "EXPIRE_DT", nullable = false)
    private java.time.LocalDateTime expireDt;

    @Column(name = "IS_VERIFIED", length = 1, nullable = false)
    @Builder.Default
    private String isVerified = "N"; // 인증 전: N, 인증 성공: Y

    public boolean isExpired() {
        return expireDt.isBefore(java.time.LocalDateTime.now());
    }

    public boolean isVerified() {
        return "Y".equals(isVerified);
    }
}