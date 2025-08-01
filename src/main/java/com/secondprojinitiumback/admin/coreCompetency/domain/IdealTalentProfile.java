package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "IDEAL_TALENT_PROFILE")
public class IdealTalentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITP_ID")
    private Long id; // 인재상 프로필 ID (PK)

    @Column(name = "ITP_NM", nullable = false)
    private String name; // 인재상 이름

}
