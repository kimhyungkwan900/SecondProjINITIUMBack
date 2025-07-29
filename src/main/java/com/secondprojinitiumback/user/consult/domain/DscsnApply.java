package com.secondprojinitiumback.user.consult.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
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
public class DscsnApply {

    @Id
    @Column(name = "DSCSN_APLY_ID", nullable = false, length = 10)
    private String dscsn_apply_id;

    @Column(name = "STDNT_TELNO", nullable = false, length = 11)
    private String student_telno;

    @Column(name = "DSCSN_APLY_CN", nullable = true)
    private String dscsn_apply_cn;

    @Column(name = "DSCSN_STATUS", nullable = false)
    private String dscsn_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_NO", foreignKey = @ForeignKey(name = "FK_STDNT_NO"))
    private Student student;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DSCSN_DT_ID", foreignKey = @ForeignKey(name = "FK_DSCSN_DT_ID"))
    private DscsnDate dscsn_dt;

    @OneToOne
    @JoinColumn(name = "DSCSN_KND_ID",foreignKey = @ForeignKey(name = "FK_DSCSN_KND_ID"))
    private DscsnKind dscsn_kind;
}
