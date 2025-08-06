package com.secondprojinitiumback.common.security.domain;

import com.secondprojinitiumback.common.converter.LocalDateTimeToChar12Converter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_USER_AUTH_INFO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginAuthInfo {

    // 로그인 인증 정보 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LGN_USER_AUTH_INFO_ID")
    private Long id;

    // 로그인 정보 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", nullable = false)
    private LoginInfo loginInfo;

    // OAuth 인증 정보
    @Column(name = "ACCESS_TOKEN", columnDefinition = "TEXT", nullable = false)
    private String accessToken;

    // Refresh Token
    @Column(name = "REFRESH_TOKEN", columnDefinition = "TEXT", nullable = false)
    private String refreshToken;

    // 발급 일시
    @Column(name = "ISSUED_AT", nullable = false, length = 12)
    @Convert(converter = LocalDateTimeToChar12Converter.class)
    private LocalDateTime issuedAt;

    // 만료 일시
    @Column(name = "EXPIRES_AT", nullable = false, length = 12)
    @Convert(converter = LocalDateTimeToChar12Converter.class)
    private LocalDateTime expiresAt;

    // 마지막 사용 일시
    @Column(name = "LAST_USED_AT")
    private LocalDateTime lastUsedAt;

    // 강제 로그아웃 여부
    @Column(name = "LGT_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private boolean isForcedLogout;
}
