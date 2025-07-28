package com.secondprojinitiumback.user.consult.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
지도교수 상담: A
진로취업 상담: C
심리상담: P
학습상담: L
 */

@Entity
@Getter
@Table(name="DSCSN_APPLY")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dscsn_apply {

    @Id
    @Column(name = "DSCSN_APPLY_ID", nullable = false, length = 5)
    private String dscsn_apply_id;

    @Column(name = "STDNT_TELNO", nullable = false, length = 11)
    private String stdnt_telno;

    @Column(name = "DSCSN_APLY_CN", nullable = true)
    private String dscsn_aply_cn;

    @Column(name = "DSCSN_STATUS", nullable = false)
    private String dscsn_status;

    @Column(name = "STDNT_NO", nullable = false)
    private Character stdnt_no;

    @Column(name = "DSCSN_DT_ID", nullable = false)
    private Character dscsn_dt_id;

    @Column(name = "DSCSN_KND_ID", nullable = true)
    private Character dscsn_knd_id;
}
