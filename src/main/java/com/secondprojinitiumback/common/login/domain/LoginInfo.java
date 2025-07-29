package com.secondprojinitiumback.common.login.domain;

import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder; // Builder import 추가
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
public class LoginInfo extends BaseEntity {

    @Id
    @Column(name = "LGN_ID", length = 20, nullable = false, unique = true)
    private String loginId;

    @Column(name = "LGN_PW", length = 100, nullable = false)
    private String password;

    @Column(name = "USER_TYPE", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'S'")
    @Pattern(regexp = "S|E|A", message = "USER_TYPE은 S(학생), E(교직원), A(역량센터) 중 하나여야 합니다.")
    private String userType = "S";

    @Column(name = "ACNT_STTS_CD", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String accountStatusCode = "N";

    // Y: 비밀번호 변경 필요, N: 비밀번호 변경 불필요
    @Column(name = "PSWD_CHG_YN", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    @Pattern(regexp = "Y|N")
    private String passwordChangeYn = "N";

    @Column(name = "LGN_FAIL_NMTM", columnDefinition = "INT DEFAULT 0")
    @Min(value = 0)
    private int loginFailCount = 0;

    @Column(name = "ACNT_JOIN_DT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime accountJoinDate;

    // 기존 create 메서드는 유지하거나 제거할 수 있습니다.
    // @Builder를 사용하면 이 메서드는 필요 없을 수 있습니다.
    public static LoginInfo create(String loginId,
                                   String password,
                                   String userType,
                                   String accountStatusCode,
                                   String passwordChangeYn,
                                   int loginFailCount,
                                   LocalDateTime accountJoinDate) {
        return new LoginInfo(loginId, password, userType, accountStatusCode, passwordChangeYn, loginFailCount, accountJoinDate);
    }

    // private 생성자 위에 @Builder 추가
    @Builder
    private LoginInfo(String loginId,
                      String password,
                      String userType,
                      String accountStatusCode,
                      String passwordChangeYn,
                      int loginFailCount,
                      LocalDateTime accountJoinDate) {
        this.loginId = loginId;
        this.password = password;
        this.userType = userType != null ? userType : "S";
        this.accountStatusCode = accountStatusCode != null ? accountStatusCode : "N";
        this.passwordChangeYn = passwordChangeYn != null ? passwordChangeYn : "N";
        this.loginFailCount = loginFailCount;
        this.accountJoinDate = accountJoinDate != null ? accountJoinDate : LocalDateTime.now();
    }

    public void increaseLoginFailCount() {
        this.loginFailCount++;
    }

    public void resetLoginFailCount() {
        this.loginFailCount = 0;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        this.passwordChangeYn = "Y";
    }
}