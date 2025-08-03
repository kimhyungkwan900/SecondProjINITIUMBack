package com.secondprojinitiumback.admin.coreCompetency.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "core_competency_response")
@NoArgsConstructor
@AllArgsConstructor
public class CoreCompetencyResponse {

    @Id
    @Column(name = "ASMT_RSPNS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 응답 ID (PK)

    // 학생 엔티티 연결
    @ManyToOne
    @JoinColumn(name = "STDNT_NO")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "QSTN_ID")
    private CoreCompetencyQuestion question; // 응답된 문항

    @OneToOne
    @JoinColumn(name = "CHC_OPT_ID", referencedColumnName = "CHC_OPT_ID")
    private ResponseChoiceOption selectedOption;

    @Column(name = "CMPLET_DT")
    private String completeDate; // 응답 완료 일자

    @Column(name = "RSPNS_RSULT_SCR")
    private Integer resultScore; // 응답 결과 점수

    @Column(name = "SLCT_CNT")
    private Integer selectCount; // 선택된 보기 수
}

