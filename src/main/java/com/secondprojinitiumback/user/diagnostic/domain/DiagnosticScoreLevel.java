package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DGNSTC_SCR_LVL")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticScoreLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_SCR_LVL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_TST_ID")
    private DiagnosticTest test;

    @Column(name = "MIN_SCR")
    private Integer minScore;

    @Column(name = "MAX_SCR")
    private Integer maxScore;

    @Column(name = "SCR_LVL_NM")
    private String levelName;

    @Lob
    @Column(name = "SCR_LVL_DC")
    private String description;
}
