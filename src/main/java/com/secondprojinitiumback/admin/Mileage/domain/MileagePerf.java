package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "MLG_PERF")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MileagePerf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLG_PERF_ID")
    private Long id; //마일리지 실적 ID

    @Column(name = "ACC_MLG", nullable = false)
    private Integer accMlg; //적립된 마일리지

    @Column(name = "CRTN_DT", nullable = false)
    private LocalDateTime createdAt; // 등록일시

    @Column(name = "CNCL_DT")
    private LocalDateTime canceledAt; // 취소일시

    @Column(name = "CNCL_RSN_CN", columnDefinition = "TEXT")
    private String cancelReason; // 취소사유

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MLG_ALTCL_ID", nullable = false)
    private MileageItem mileageItem; // 마일리지 항목

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "MLG_SCOR_PLCY_ID", nullable = true)
    private ScorePolicy scorePolicy; // 배점 정책

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHLR_APLY_ID")
    private ScholarshipApply scholarshipApply; // 장학금 신청

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_NO", referencedColumnName = "STDNT_NO", nullable = false)
    private Student student; // 학생 정보

    // 실적이 취소된 상태인지 확인하는
    public boolean isCanceled() {

        return canceledAt != null;
    }
}
