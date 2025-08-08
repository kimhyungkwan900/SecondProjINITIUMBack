package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "ideal_talent_profile")
public class IdealTalentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITP_ID")
    private Long id; // 인재상 프로필 ID (PK)

    @Column(name = "ITP_NM", nullable = false)
    private String name; // 인재상 이름

    //프론트에서 트리구조를 출력하기 위한 양방향 설정
    @OneToMany(mappedBy = "idealTalentProfile")
    @JsonIgnore
    private List<CoreCompetencyCategory> coreCompetencyCategories = new ArrayList<>();


}
