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

    @Column(name="OPT_NO", nullable=false)   // 1..N
    private Integer optionNo;   // // 보기 번호 (객관식 보기의 순서, 1부터 시작)

    @ManyToOne
    @JoinColumn(name = "QSTN_ID", referencedColumnName = "QSTN_ID")
    private CoreCompetencyQuestion question;

    @Column(name = "CHC_OPT_LBL")
    private String label; // 보기 라벨

    @Column(name = "OPT_SCR")
    private Integer score; // 해당 보기에 대한 점수

    @Column(name="ANSR_TYPE", nullable=false, length=10)
    private String answerType = "SINGLE"; // 답변방식, 단일문항답변으로 고정(결과 계산을 위해)

    public void updateDetails(String label, Integer score) {
        this.label = label;
        this.score = score;
    }
}
