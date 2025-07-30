package com.secondprojinitiumback.common.login.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_HISTORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LoginHistory {

    // 로그인 기록 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LGN_HISTORY_ID", nullable = false)
    private Long id;

    // 로그인 정보 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", nullable = false)
    private LoginInfo loginInfo;

    // 로그인 일시
    @Column(name = "LGN_DT")
    private LocalDateTime loginDateTime;

    // 로그아웃 일시
    @Column(name = "LGT_DT")
    private LocalDateTime logoutDateTime;

    // 로그인 IP 주소
    @Column(name = "LGN_IP_ADDR", length = 15)
    private String loginIpAddress;

    // 로그인 성공 여부
    @Column(name = "LGN_YN", length = 1)
    @JdbcTypeCode(SqlTypes.CHAR)
    private boolean isSuccessful;

}
