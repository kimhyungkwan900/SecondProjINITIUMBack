package com.secondprojinitiumback.admin.coreCompetency.entity;

import com.secondprojinitiumback.common.domain.CommonCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CORE_COMPETENCY_QUESTION")
public class CoreCompetencyQuestion {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "QSTN_ID")
    private Long id; // 문항 ID (기본 키)

    @ManyToOne
    @JoinColumn(name = "ASMT_ID")
    private CoreCompetencyAssessment assessment; // 연관된 평가 (외래 키: 평가 ID)

    @Column(name = "QSTN_NO")
    private Integer questionNo; // 문항 번호 (평가 내에서의 고유 번호)

    @Column(name = "QSTN_NM")
    private String name; // 문항 내용 (실제 질문 텍스트)

    @Column(name = "DSPY_ORD_NO")
    private Integer displayOrder; // 문항 표시 순서

    @Column(name = "ANSR_ALOW_CNT")
    private Integer answerAllowCount; // 허용 응답 개수 (객관식 보기 중 선택 가능 개수)

    @OneToOne
    @JoinColumn(name = "INDCTR_ID", nullable = false)
    private BehaviorIndicator behaviorIndicator; // 행동 지표 ID (외래 키, insertable=false, updatable=false)

}
