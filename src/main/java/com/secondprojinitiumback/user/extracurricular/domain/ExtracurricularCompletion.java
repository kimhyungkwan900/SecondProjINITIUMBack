package com.secondprojinitiumback.user.extracurricular.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "extracurricular_completion")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularCompletion {
    @Id
    @Column(name = "edu_fnsh_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduFnshId; // 수료 여부 ID

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_aply_id")
    private ExtracurricularApply extracurricularApply; // 해당 신청과의 관계 (읽기 전용)

    @Column(name = "edu_fnsh_yn")
    private String eduFnshYn; // 수료 여부 (Y/N)

    @Column(name = "edu_fnsh_dt")
    private LocalDateTime eduFnshDt; // 수료 일시


}
