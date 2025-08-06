package com.secondprojinitiumback.common.security.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "LGN_INFO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "LGN_ID")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInfo {

    // 로그인 ID는 최대 20자로 제한되며, 유일해야 함
    @Id
    @Column(name = "LGN_ID", length = 20, nullable = false, unique = true)
    private String loginId;

    // 비밀번호는 암호화되어 저장되며, 최대 길이는 100자로 제한 Bcrypt 대응
    @Column(name = "LGN_PSWD", length = 100, nullable = false)
    private String password;


    // 사용자 유형 코드: S(학생), E(교직원), A(역량센터)
    @Column(name = "USER_TYPE", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'S'")
    @Pattern(regexp = "S|E|A", message = "USER_TYPE은 S(학생), E(교직원), A(역량센터) 중 하나여야 합니다.")
    private String userType = "S";

    // 계정 상태 코드: N(정상), L(잠김), D(삭제)
    @Column(name = "ACNT_STTS_CD", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String accountStatusCode = "N";

    // true: 비밀번호 변경 필요, false: 비밀번호 변경 불필요
    @Column(name = "PSWD_CHG_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private boolean passwordChangeRequired;

    // 로그인 실패 횟수
    @Column(name = "LGN_FAIL_NMTM", columnDefinition = "INT DEFAULT 0")
    @Min(value = 0)
    private int loginFailCount = 0;

    // 계정 가입 일시
    @Column(name = "ACNT_JOIN_DT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime accountJoinDate;

    // 마지막 비밀번호 변경 일시
    @Column(name = "LAST_PSWD_CHG_DT")
    private LocalDateTime lastPasswordChangeDateTime;

    // 마지막 로그인 일시
    @Column(name = "LAST_LOGIN_DT")
    private LocalDateTime lastLoginDateTime;

    @Builder
    private LoginInfo(String loginId,
                      String password,
                      String userType,
                      String accountStatusCode,
                      boolean passwordChangeRequired,
                      int loginFailCount,
                      LocalDateTime accountJoinDate,
                      LocalDateTime lastPasswordChangeDateTime) {
        this.loginId = loginId;
        this.password = password;
        this.userType = userType != null ? userType : "S";
        this.accountStatusCode = accountStatusCode != null ? accountStatusCode : "N";
        this.passwordChangeRequired = passwordChangeRequired;
        this.loginFailCount = loginFailCount;
        this.accountJoinDate = accountJoinDate != null ? accountJoinDate : LocalDateTime.now();
        this.lastPasswordChangeDateTime = lastPasswordChangeDateTime;
    }

    public void increaseLoginFailCount() {
        this.loginFailCount++;
    }

    public void resetLoginFailCount() {
        this.loginFailCount = 0;
    }

    public void changePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
        this.passwordChangeRequired = false;
        this.lastPasswordChangeDateTime = LocalDateTime.now();
        this.loginFailCount = 0;
    }

    public void issueTemporaryPassword(String encodedTemporaryPassword) {
        this.password = encodedTemporaryPassword;
        this.passwordChangeRequired = true;
        this.lastPasswordChangeDateTime = LocalDateTime.now();
        this.loginFailCount = 0;
    }

    public void lockAccount() { this.accountStatusCode = "L"; }
    public void unlockAccount() { this.accountStatusCode = "N"; }

    public boolean isPasswordExpired() {
        return lastPasswordChangeDateTime != null &&
                lastPasswordChangeDateTime.isBefore(LocalDateTime.now().minusMonths(6));
    }
    public void updateLastLoginDateTime() {
        this.lastLoginDateTime = LocalDateTime.now();
    }

    public void updateLastPasswordChangeDateTime() {
        this.lastPasswordChangeDateTime = LocalDateTime.now();
        this.passwordChangeRequired = false;
    }

}
