package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DGNSTC_RSLT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_RSLT_ID")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId; // 외래키로 Student 참조 (필요시 @ManyToOne)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_TST_ID")
    private DiagnosticTest test;

    @Column(name = "TOT_SCR")
    private Integer totalScore;

    @Column(name = "CMPLTN_DT")
    private LocalDateTime completionDate;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL)
    private List<DiagnosticResultDetail> details = new ArrayList<>();
}
