package com.secondprojinitiumback.common.security.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LGN_INFO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginInfo {

    @Id
    @Column(name = "LGN_ID", length = 20, nullable = false)
    private String loginId;

    // 로그인 비밀번호
    @Column(name = "LGN_PSWD", length = 100, nullable = false)
    private String password;

    // S(학생)/E(교직원)/A(역량센터)
    @Column(name = "USER_TYPE", length = 1, nullable = false)
    @Pattern(regexp = "S|E|A", message = "USER_TYPE은 S/E/A 중 하나여야 합니다.")
    private String userType;

    // N(정상)/L(잠김)/D(삭제)/T(임시비밀번호)
    @Column(name = "ACNT_STTS_CD", length = 1, nullable = false)
    private String accountStatusCode;

    // Y(비밀번호 변경 필요)/N(비밀번호 변경 불필요)
    @Column(name = "PSWD_CHG_YN", length = 1, nullable = false)
    private Boolean passwordChangeRequired;

    // 로그인 실패 횟수 (최대 5회)
    @Column(name = "LGN_FAIL_NMTM", nullable = false)
    @Min(0)
    private int loginFailCount;

    // 계정 가입 일시
    @Column(name = "ACNT_JOIN_DT", nullable = false)
    private LocalDateTime accountJoinDate;

    // 비밀번호 변경 일시
    @Column(name = "LAST_PSWD_CHG_DT")
    private LocalDateTime lastPasswordChangeDateTime;

    // 마지막 로그인 일시
    @Column(name = "LAST_LOGIN_DT")
    private LocalDateTime lastLoginDateTime;

    /* ===== 연관관계: LoginAuthInfo / LoginHistory ===== */
    @OneToMany(mappedBy = "loginInfo", fetch = FetchType.LAZY)
    private List<LoginAuthInfo> authInfos = new ArrayList<>();

    @OneToMany(mappedBy = "loginInfo", fetch = FetchType.LAZY)
    private List<LoginHistory> loginHistories = new ArrayList<>();

    @Builder
    private LoginInfo(String loginId,
                      String password,
                      String userType,
                      String accountStatusCode,
                      Boolean passwordChangeRequired,
                      Integer loginFailCount,
                      LocalDateTime accountJoinDate,
                      LocalDateTime lastPasswordChangeDateTime,
                      LocalDateTime lastLoginDateTime) {
        this.loginId = loginId;
        this.password = password;
        this.userType = (userType != null ? userType : "S");
        this.accountStatusCode = (accountStatusCode != null ? accountStatusCode : "N");
        this.passwordChangeRequired = (passwordChangeRequired != null ? passwordChangeRequired : Boolean.TRUE);
        this.loginFailCount = (loginFailCount != null ? loginFailCount : 0);
        this.accountJoinDate = (accountJoinDate != null ? accountJoinDate : LocalDateTime.now());
        this.lastPasswordChangeDateTime = lastPasswordChangeDateTime;
        this.lastLoginDateTime = lastLoginDateTime;
    }

    /* ===== 도메인 메서드 ===== */
    public void increaseLoginFailCount() { this.loginFailCount++; }
    public void resetLoginFailCount() { this.loginFailCount = 0; }

    // 비밀번호 변경
    public void changePassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
        this.passwordChangeRequired = Boolean.FALSE;
        this.lastPasswordChangeDateTime = LocalDateTime.now();
        this.loginFailCount = 0;
        this.accountStatusCode = "N"; // 비밀번호 변경 후 정상 상태로 변경
    }

    // 임시 비밀번호 발급
    public void issueTemporaryPassword(String encodedTemporaryPassword) {
        this.password = encodedTemporaryPassword;
        this.passwordChangeRequired = Boolean.TRUE;
        this.lastPasswordChangeDateTime = LocalDateTime.now();
        this.loginFailCount = 0;
        this.accountStatusCode = "T"; // 임시비밀번호 상태로 설정
    }

    // 계정 상태 변경
    public void lockAccount()   { this.accountStatusCode = "L"; }
    public void unlockAccount() { this.accountStatusCode = "N"; }

    // 비밀번호 변경 필요 여부
    public boolean isPasswordExpired() {
        return lastPasswordChangeDateTime != null &&
                lastPasswordChangeDateTime.isBefore(LocalDateTime.now().minusMonths(6));
    }

    // 마지막 로그인 일시 업데이트
    public void updateLastLoginDateTime() {
        this.lastLoginDateTime = LocalDateTime.now();
    }

    // 양방향 편의 메서드
    void addAuthInfo(LoginAuthInfo auth) {
        this.authInfos.add(auth);
        auth.setLoginInfoInternal(this);
    }
    void addLoginHistory(LoginHistory history) {
        this.loginHistories.add(history);
        history.setLoginInfoInternal(this);
    }
}
