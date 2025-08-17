package com.secondprojinitiumback.common.security.domain;

import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "PSWD_HIST")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PSWD_HIST_ID")
    private Long passwordHistoryId;

    @Column(name = "LGN_ID", length = 20, nullable = false)
    private String loginId;

    @Column(name = "ENC_PSWD", length = 100, nullable = false)
    private String encryptedPassword;

    @Column(name = "CHG_DT", nullable = false)
    private LocalDateTime changeDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", insertable = false, updatable = false)
    private LoginInfo loginInfo;

    @Builder
    private PasswordHistory(String loginId, String encryptedPassword, LocalDateTime changeDateTime) {
        this.loginId = loginId;
        this.encryptedPassword = encryptedPassword;
        this.changeDateTime = changeDateTime != null ? changeDateTime : LocalDateTime.now();
    }

    public static PasswordHistory create(String loginId, String encryptedPassword) {
        return PasswordHistory.builder()
                .loginId(loginId)
                .encryptedPassword(encryptedPassword)
                .changeDateTime(LocalDateTime.now())
                .build();
    }
}