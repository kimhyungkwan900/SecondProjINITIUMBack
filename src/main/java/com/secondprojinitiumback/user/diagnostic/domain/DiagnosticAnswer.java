package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DGNSTC_ANS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_ANS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_QSTN_ID")
    private DiagnosticQuestion question;

    @Column(name = "ANS_CN", length = 100)
    private String content;

    @Column(name = "SCR")
    private Integer score;

    @Column(name = "SELT_VAL")
    private Integer selectValue;
}
