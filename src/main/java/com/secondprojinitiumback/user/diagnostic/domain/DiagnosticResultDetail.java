package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DGNSTC_RSLT_DTL")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResultDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_RSLT_DTL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_RSLT_ID")
    private DiagnosticResult result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_QSTN_ID")
    private DiagnosticQuestion question;

    @Column(name = "SELT_VAL")
    private Integer selectedValue;

    @Column(name = "SCR")
    private Integer score;
}
