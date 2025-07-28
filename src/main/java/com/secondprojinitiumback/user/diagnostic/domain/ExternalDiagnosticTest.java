package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EX_DGNSTC_TST")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosticTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EX_DGNSTC_TST_ID")
    private Long id;

    @Column(name = "EX_DGNSTC_TST_NM")
    private String name;

    @Column(name = "PRVD_NM")
    private String provider;

    @Column(name = "QESTRN_SEQ_NO")
    private String questionApiCode;

    @Column(name = "TRGET_SE_CD")
    private String targetCode;

    @Lob
    @Column(name = "DGNSTC_TST_DC")
    private String description;
}
