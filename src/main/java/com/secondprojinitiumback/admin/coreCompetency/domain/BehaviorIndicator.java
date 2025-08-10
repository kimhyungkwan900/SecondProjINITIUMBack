package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "behavior_indicator")
public class BehaviorIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INDCTR_ID")
    private Long id; // 행동 지표 ID (기본 키)

    @Column(name = "INDCTR_NM", nullable = false)
    private String name; // 행동 지표 이름 (예: "적극성", "협업 능력" 등)

    @ManyToOne
    @JoinColumn(name = "STGR_ID", nullable = false)
    private SubCompetencyCategory subCompetencyCategory; // 하위 역량 카테고리 (외래 키: 하위 역량 ID)

    //양방향 설정
    @OneToOne(mappedBy = "behaviorIndicator", fetch = FetchType.LAZY)
    private CoreCompetencyQuestion questions;
}
