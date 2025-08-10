package com.secondprojinitiumback.common.security.domain;

import com.secondprojinitiumback.common.converter.LocalDateTimeToChar12Converter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_USER_AUTH_INFO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class LoginAuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LGN_USER_AUTH_INFO_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", nullable = false)
    private LoginInfo loginInfo;

    // 로그인 인증 정보는 Access Token과 Refresh Token을 포함
    @Column(name = "ACCESS_TOKEN", columnDefinition = "TEXT", nullable = false)
    private String accessToken;

    @Column(name = "REFRESH_TOKEN", columnDefinition = "TEXT", nullable = false)
    private String refreshToken;

    // 발급 일시와 만료 일시
    @Column(name = "ISSUED_AT", nullable = false, length = 12)
    @Convert(converter = LocalDateTimeToChar12Converter.class)
    private LocalDateTime issuedAt;

    @Column(name = "EXPIRES_AT", nullable = false, length = 12)
    @Convert(converter = LocalDateTimeToChar12Converter.class)
    private LocalDateTime expiresAt;

    // 마지막 사용 일시
    @Column(name = "LAST_USED_AT")
    private LocalDateTime lastUsedAt;

    // Y/N → Boolean (autoApply 컨버터)
    @Column(name = "LGT_YN", length = 1, nullable = false)
    private Boolean forcedLogout;

    /* ===== 생성 팩토리 ===== */
    public static LoginAuthInfo issue(LoginInfo loginInfo,
                                      String accessToken,
                                      String refreshToken,
                                      LocalDateTime issuedAt,
                                      LocalDateTime expiresAt) {
        return LoginAuthInfo.builder()
                .loginInfo(loginInfo)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .lastUsedAt(null)
                .forcedLogout(Boolean.FALSE)
                .build();
    }


    public void markUsed(LocalDateTime usedAt) {
        this.lastUsedAt = (usedAt != null ? usedAt : LocalDateTime.now());
    }

    public void rotateTokens(String newAccessToken,
                             String newRefreshToken,
                             LocalDateTime newIssuedAt,
                             LocalDateTime newExpiresAt) {
        if (newAccessToken == null || newRefreshToken == null) {
            throw new IllegalArgumentException("새 토큰(AT/RT)이 필요합니다.");
        }
        this.accessToken = newAccessToken;
        this.refreshToken = newRefreshToken;
        this.issuedAt = (newIssuedAt != null ? newIssuedAt : LocalDateTime.now());
        this.expiresAt = newExpiresAt;
    }

    public void forceLogout() { this.forcedLogout = Boolean.TRUE; }
    public void clearForceLogout() { this.forcedLogout = Boolean.FALSE; }

    public void invalidateAll() {
        this.accessToken = "";
        this.refreshToken = "";
        this.forcedLogout = Boolean.TRUE;
        this.lastUsedAt = LocalDateTime.now();
    }

    /* 양방향 내부 세터 (외부 공개 금지) */
    void setLoginInfoInternal(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
