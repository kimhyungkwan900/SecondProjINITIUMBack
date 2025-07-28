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

    /**
     * DiagnosticQuestion과 다대일(N:1) 관계 설정
     * 여러 개의 DiagnosticAnswer가 하나의 DiagnosticQuestion에 속함
     * fetch = LAZY: 실제로 참조할 때만 로딩 (성능 최적화)
     * @JoinColumn(name = "DGNSTC_QSTN_ID"): 외래키 컬럼명 지정
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_QSTN_ID")
    private DiagnosticQuestion question;

    /**
     * 답변 내용 컬럼
     * 길이 제한: 최대 100자
     */
    @Column(name = "ANS_CN", length = 100)
    private String content;

    /**
     * 답변 점수 컬럼
     * 해당 답변이 선택될 때 부여되는 점수 저장
     */
    @Column(name = "SCR")
    private Integer score;

    /**
     * 선택 값 컬럼
     * 사용자가 선택할 수 있는 값(선택지 식별자)
     */
    @Column(name = "SELT_VAL")
    private Integer selectValue;
}
