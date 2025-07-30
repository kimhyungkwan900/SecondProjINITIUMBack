package com.secondprojinitiumback.common.login.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_AUTH_INFO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginAuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LGN_USER_AUTH_INFO_ID")
    private Long id; // 인증정보 PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", nullable = false)
    private LoginInfo loginInfo; // 로그인 ID (FK)

    @Column(name = "ACCESS_TOKEN", columnDefinition = "TEXT", nullable = false)
    private String accessToken;

    @Column(name = "REFRESH_TOKEN", columnDefinition = "TEXT", nullable = false)
    private String refreshToken;

    @Column(name = "ISSUED_AT", nullable = false)
    private LocalDateTime issuedAt; // 발급 시각

    @Column(name = "EXPIRES_AT", nullable = false)
    private LocalDateTime expiresAt; // 만료 시각

    @Column(name = "LAST_USED_AT")
    private LocalDateTime lastUsedAt; // 마지막 접근 일시

    @Column(name = "LGT_YN", length = 1)
    private String forceLogoutYn; // 강제 로그아웃 여부 (Y/N)
}