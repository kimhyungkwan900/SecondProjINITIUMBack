package com.secondprojinitiumback.admin.coreCompetency.domain;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITP_ID", nullable = false)
    private IdealTalentProfile idealTalentProfile; // 인재상 프로필과 연관

    //양방향 설정
    @OneToMany(mappedBy = "coreCompetencyCategory", fetch = FetchType.LAZY)
    private List<SubCompetencyCategory> subCompetencyCategories = new ArrayList<>();

}

