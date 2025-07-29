package com.secondprojinitiumback.common.login.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LGN_HISTORY_ID", nullable = false)
    private Long id; // 로그인기록 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGNID", nullable = false)
    private LoginInfo loginInfo; // 로그인 ID (FK)

    @Column(name = "LGN_DT")
    private LocalDateTime loginDateTime; // 로그인일시

    @Column(name = "LGT_DT")
    private LocalDateTime logoutDateTime; // 로그아웃일시

    @Column(name = "LGN_IP_ADDR", length = 15)
    private String loginIpAddress; // 로그인 IP 주소

    @Column(name = "LGN_YN", length = 1)
    private String loginSuccessYn; // 로그인 성공 여부 (Y/N)

    @Column(name = "LAST_LGN_DT")
    private LocalDateTime lastLoginDateTime; // 마지막 로그인일시

    @Column(name = "LAST_PSWD_CHG_DT")
    private LocalDateTime lastPasswordChangeDateTime; // 마지막 비밀번호 변경일자
}