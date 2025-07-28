package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "EX_DGNSTC_RSLT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosticResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EX_DGNSTC_RSLT_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EX_DGNSTC_TST_ID")
    private ExternalDiagnosticTest test;

    @Column(name = "INSPECT_SEQ_NO")
    private String inspectCode;

    @Lob
    @Column(name = "RSLT_URL")
    private String resultUrl;

    @Column(name = "SBMSN_DT")
    private LocalDateTime submittedAt;
}
