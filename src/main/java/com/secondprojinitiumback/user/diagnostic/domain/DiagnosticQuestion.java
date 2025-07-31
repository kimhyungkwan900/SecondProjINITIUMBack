package com.secondprojinitiumback.user.diagnostic.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DGNSTC_QSTN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_QSTN_ID")
    private Long id;

    /**
     * DiagnosticTest와 다대일(N:1) 관계
     * 여러 개의 DiagnosticQuestion이 하나의 DiagnosticTest에 속함
     * fetch = LAZY: 필요할 때만 로드 (성능 최적화)
     * @JoinColumn(name = "DGNSTC_TST_ID"): 외래 키 컬럼명 지정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_TST_ID")
    private DiagnosticTest test;

    /**
     * 질문 내용 (대용량 텍스트 가능)
     * @Lob: TEXT 타입으로 매핑
     */
    @Lob
    @Column(name = "QSTN_CN")
    private String content;

    /**
     * 질문 순서
     * 검사 문항 순서 관리
     */
    @Column(name = "QSTN_ORD")
    private Integer order;

    /**
     * 답변 타입
     * Enum을 문자열(VARCHAR)로 저장
     * 예: YES_NO, SCALE_5, SCALE_7
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ANS_TY_CD", length = 30)
    private AnswerType answerType;

    /**
     * DiagnosticAnswer와 일대다(1:N) 관계
     * 하나의 DiagnosticQuestion이 여러 DiagnosticAnswer를 가짐
     * mappedBy = "question": DiagnosticAnswer의 question 필드에 매핑됨
     * CascadeType.ALL: Question 저장/삭제 시 Answer도 함께 처리
     */
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiagnosticAnswer> answers = new ArrayList<>();
}
