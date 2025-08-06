package com.secondprojinitiumback.admin.coreCompetency.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "response_choice_option")
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChoiceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHC_OPT_ID")
    private Long id; // 보기 ID

    @Column(name = "CHC_OPT_LBL")
    private String label; // 보기 라벨

    @Column(name = "OPT_SCR")
    private Integer score; // 해당 보기에 대한 점수
}
