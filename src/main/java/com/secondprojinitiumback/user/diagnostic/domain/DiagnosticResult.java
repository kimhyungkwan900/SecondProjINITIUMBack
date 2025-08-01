package com.secondprojinitiumback.user.diagnostic.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DGNSTC_RSLT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_RSLT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_NO", referencedColumnName = "STDNT_NO")
    private Student student;


    /**
     * DiagnosticTest와 다대일(N:1) 관계
     * 하나의 검사(DiagnosticTest)에 대해 여러 결과(DiagnosticResult)가 존재
     * 지연 로딩(LAZY)으로 성능 최적화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DGNSTC_TST_ID")
    private DiagnosticTest test;

    /**
     * 총점 (해당 검사 결과의 점수 합계)
     */
    @Column(name = "TOT_SCR")
    private Integer totalScore;

    /**
     * 검사 완료 일시
     */
    @Column(name = "CMPLTN_DT")
    private LocalDateTime completionDate;

    /**
     * DiagnosticResultDetail과 일대다(1:N) 관계
     * 하나의 결과(DiagnosticResult)에 여러 상세 응답(DiagnosticResultDetail)이 매핑
     * mappedBy = "result": Detail 엔티티에서 매핑된 필드 이름
     * CascadeType.ALL: Result 저장/삭제 시 Detail도 함께 처리
     */
    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiagnosticResultDetail> details = new ArrayList<>();
}
