package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MLG_SCOR_PLCY")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ScorePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLG_SCOR_PLCY_ID")
    private Long id; //배점 정책 ID(FK)

    @Column(name = "SCR_CRTR_CN", length = 50)
    private String scoreCriteria; //배점 조건 설명

    @Column(name = "REQUIRED_ATTENDANCE")
    private Integer requiredAttendance; // 최소 출석 조건

    @Column(name = "SCORE_RATE")
    private Double scoreRate; // 지급 비율 (0.5, 1.0 등)

    @Column(name = "USE_YN", length = 1)
    private String useYn; // 사용 여부 ('Y', 'N')

    @Column(name = "CRTN_DT")
    private LocalDateTime createdAt; // 등록일자

    // 비교과 프로그램 (외래키로 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDU_MNG_ID")
    private ExtracurricularProgram program;

    // 마일리지 항목 (외래키로 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MLG_ALTCL_ID")
    private MileageItem mileageItem;

}
