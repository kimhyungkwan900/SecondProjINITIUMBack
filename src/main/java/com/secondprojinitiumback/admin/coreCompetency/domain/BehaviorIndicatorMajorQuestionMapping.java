package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.secondprojinitiumback.common.domain.SchoolSubject;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "BI_Q_MAJOR_MAP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BehaviorIndicatorMajorQuestionMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MPNG_ID")
    private Long id; // 매핑 ID

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "QSTN_ID", nullable = false)
    private CoreCompetencyQuestion question; // 문항

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INDCTR_ID", nullable = false)
    private BehaviorIndicator behaviorIndicator; // 행동지표

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCSBJT_NO", nullable = false)
    private SchoolSubject schoolSubject; // 학과
}
