package com.secondprojinitiumback.user.consult.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="DSCSN_INFO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DscsnInfo {

    @Id
    @Column(name = "DSCSN_INFO_ID", nullable = false, length = 5)
    private String dscsnInfoId; //상담일정 ID

    @Column(name = "DSCSN_STATUS", nullable = false)
    private String dscsnStatus; //상담 상태

    @Column(name = "DSCSN_RSLT_CN", nullable = true)
    private String dscsnResultCn; //상담결과 내용

    @Column(name = "DSCSN_RLS_YN", nullable = false, length = 1)
    private String dscsnReleaseYn; //상담결과 공개여부

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DSCSN_APLY_ID", foreignKey = @ForeignKey(name = "FK_DSCSN_APLY_ID"))
    private DscsnApply dscsnApply;  //신청서 ID
}
