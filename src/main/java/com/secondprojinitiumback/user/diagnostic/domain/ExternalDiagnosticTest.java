package com.secondprojinitiumback.user.diagnostic.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EX_DGNSTC_TST")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalDiagnosticTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EX_DGNSTC_TST_ID")
    private Long id;

    /**
     * 외부 진단검사 이름
     * 예) "직업가치관검사 - 대학생", "직업적성검사"
     */
    @Column(name = "EX_DGNSTC_TST_NM")
    private String name;

    /**
     * 제공 기관명
     * 예) "커리어넷"
     */
    @Column(name = "PRVD_NM")
    private String provider;

    /**
     * 외부 API 호출 시 필요한 검사 코드
     * 커리어넷 API의 qestrn_seq 파라미터 값 저장
     */
    @Column(name = "QESTRN_SEQ_NO")
    private String questionApiCode;

    /**
     * 외부 API 호출 시 필요한 대상 코드
     * 커리어넷 API의 trget_se 파라미터 값 저장
     * 예) "100208" (대학생)
     */
    @Column(name = "TRGET_SE_CD")
    private String targetCode;

    /**
     * 외부 진단검사 설명
     * 어떤 목적/대상/내용인지 설명
     */
    @Lob
    @Column(name = "DGNSTC_TST_DC")
    private String description;

    /**
     * 🔹 공통코드 참조 (업무구분: NC)
     * CD_SE = 업무구분 그룹 (예: "C0002")
     * CD = 코드 값 (예: "NC")
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CATEGORY_CD", referencedColumnName = "CD", insertable = false, updatable = false),
            @JoinColumn(name = "CATEGORY_GRP", referencedColumnName = "CD_SE", insertable = false, updatable = false)
    })
    private CommonCode categoryCode;

    @Column(name = "CATEGORY_GRP")
    @Builder.Default
    private String categoryGroup = "CO0002";  // 업무구분 그룹 코드

    @Column(name = "CATEGORY_CD")
    @Builder.Default
    private String categoryValue = "NC";     // 업무구분 코드 값
}
