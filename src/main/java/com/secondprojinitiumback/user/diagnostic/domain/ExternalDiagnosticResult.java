package com.secondprojinitiumback.user.diagnostic.domain;

import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "EX_DGNSTC_RSLT")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosticResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EX_DGNSTC_RSLT_ID")
    private Long id;

    /**
     * Student와 다대일(N:1) 관계
     * 하나의 학생(Student)에 대해 여러 외부 검사 결과가 저장될 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STDNT_NO", referencedColumnName = "STDNT_NO")
    private Student student;

    /**
     * 외부 진단검사(ExternalDiagnosticTest)와 다대일(N:1) 관계
     * 하나의 외부검사(Test)에 여러 사용자의 결과가 매핑됨
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EX_DGNSTC_TST_ID")
    private ExternalDiagnosticTest test;

    /**
     * 외부 API에서 발급되는 검사 고유 번호
     * (커리어넷 API에서 반환하는 inspectSeq 값)
     */
    @Column(name = "INSPECT_SEQ_NO")
    private String inspectCode;

    /**
     * 외부 검사 결과 URL
     * 커리어넷에서 제공하는 결과 페이지 링크 저장
     */
    @Lob
    @Column(name = "RSLT_URL")
    private String resultUrl;

    /**
     * 외부 검사 제출 일시
     */
    @Column(name = "SBMSN_DT")
    private LocalDateTime submittedAt;
}
