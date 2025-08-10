package com.secondprojinitiumback.common.security.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LGN_HISTORY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", nullable = false)
    private LoginInfo loginInfo;

    @Column(name = "LGN_DT", nullable = false)
    private LocalDateTime loginDateTime;

    @Column(name = "LGT_DT")
    private LocalDateTime logoutDateTime;

    @Column(name = "LGN_IP_ADDR", length = 45)
    private String loginIpAddress;

    @Column(name = "LGN_YN", length = 1, nullable = false)
    private Boolean loginSuccess;

    /* ===== 팩토리 ===== */
    public static LoginHistory success(LoginInfo loginInfo, String ip) {
        return LoginHistory.builder()
                .loginInfo(loginInfo)
                .loginDateTime(LocalDateTime.now())
                .loginIpAddress(ip)
                .loginSuccess(Boolean.TRUE)
                .build();
    }

    public static LoginHistory fail(LoginInfo loginInfo, String ip) {
        return LoginHistory.builder()
                .loginInfo(loginInfo)
                .loginDateTime(LocalDateTime.now())
                .loginIpAddress(ip)
                .loginSuccess(Boolean.FALSE)
                .build();
    }

    /* ===== 행위 ===== */
    public void markLogoutNow() {
        this.logoutDateTime = LocalDateTime.now();
    }

    /* 양방향 내부 세터 (외부 공개 금지) */
    void setLoginInfoInternal(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
