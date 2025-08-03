package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "core_competency_category")
public class CoreCompetencyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CTGR_ID")
    private Long id; // 핵심역량 카테고리 ID (PK)

    @Column(name = "CTGR_NM", nullable = false)
    private String coreCategoryName; // 핵심역량 카테고리 이름

    @Column(name = "CTGR_CN", nullable = false)
    private String coreCategoryNote; // 핵심역량 카테고리 설명

    @ManyToOne
    @JoinColumn(name = "ITP_ID", nullable = false)
    private IdealTalentProfile idealTalentProfile; // 인재상 프로필과 연관

}

