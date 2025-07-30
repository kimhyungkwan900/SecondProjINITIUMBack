package com.secondprojinitiumback.common.bank.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACTNO_INFO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankAccount {
    @Id
    @Column(name = "ACTNO", length = 20)
    private String accountNo;

    @Column(name = "OWNER_ID", length = 10, nullable = false)
    private String ownerId;

    @Column(name = "ACT_TYPE", length = 4, nullable = false)
    @Pattern(regexp = "DPST|RMBR", message = "계좌 유형은 DPST 또는 RMBR만 가능합니다.")
    private String accountType;

    @Column(name = "BACNT_USE_YN", length = 1, nullable = false)
    @Pattern(regexp = "Y|N", message = "계좌 사용 여부는 Y 또는 N만 가능합니다.")
    private String useYn = "Y";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CD", referencedColumnName = "id.code"),
            @JoinColumn(name = "CD_SE", referencedColumnName = "id.codeGroup")
    })
    private CommonCode bankCode;

    @Builder
    public BankAccount(String accountNo, String ownerId, String accountType, String useYn, CommonCode bankCode) {
        this.accountNo = accountNo;
        this.ownerId = ownerId;
        this.accountType = accountType;
        this.useYn = (useYn == null) ? "Y" : useYn;
        this.bankCode = bankCode;
    }
}
