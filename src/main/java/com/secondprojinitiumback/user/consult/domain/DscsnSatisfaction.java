package com.secondprojinitiumback.user.consult.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="DSCSN_DGSTFN")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DscsnSatisfaction {

    @Id
    @Column(name = "DSCSN_DGSTFN_ID", nullable = false, length = 5)
    private String dscsnSatisfyId; //상담 만족도 ID

    @Column(name = "DSCSN_DGSTFN_NM", nullable = false, length = 5)
    private String dscsnSatisfyScore; //만족도 지표

    @Column(name = "DSCSN_DGSTFN_CN", nullable = false)
    private String dscsnImp; //개선점

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DSCSN_INFO_ID", foreignKey = @ForeignKey(name = "FK_DSCSN_INFO_ID"))
    private DscsnInfo dscsnInfo;  //신청서 ID
}
