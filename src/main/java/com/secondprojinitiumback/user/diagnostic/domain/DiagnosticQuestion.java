package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DGNSTC_QSTN")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DiagnosticQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_QSTN_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_TST_ID")
    private DiagnosticTest test;

    @Lob
    @Column(name = "QSTN_CN")
    private String content;

    @Column(name = "QSTN_ORD")
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "ANS_TY_CD", length = 30)
    private AnswerType answerType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<DiagnosticAnswer> answers = new ArrayList<>();
}
