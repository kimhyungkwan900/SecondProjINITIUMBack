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

    /**
     * DiagnosticResult와 다대일(N:1) 관계
     * 하나의 결과(DiagnosticResult)에 여러 상세 응답이 매핑됨
     * 지연 로딩(LAZY)으로 불필요한 조회 방지
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_RSLT_ID")
    private DiagnosticResult result;

    /**
     * DiagnosticQuestion와 다대일(N:1) 관계
     * 하나의 질문(DiagnosticQuestion)에 대해 여러 응답 상세가 매핑됨
     * 지연 로딩(LAZY) 적용
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_QSTN_ID")
    private DiagnosticQuestion question;

    /**
     * 사용자가 선택한 값
     * 보기의 SelectValue와 매핑
     */
    @Column(name = "SELT_VAL")
    private Integer selectedValue;

    /**
     * 해당 응답의 점수
     * 선택값에 따라 부여된 점수 저장
     */
    @Column(name = "SCR")
    private Integer score;
}
