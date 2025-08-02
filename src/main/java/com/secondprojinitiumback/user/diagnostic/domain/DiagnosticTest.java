package com.secondprojinitiumback.user.diagnostic.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "DGNSTC_TST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosticTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DGNSTC_TST_ID")
    private Long id;

    @Column(name = "DGNSTC_TST_NM", nullable = false)
    private String name;

    @Lob
    @Column(name = "DGNSTC_TST_DC")
    private String description;

    @Column(name = "USE_YN", length = 1)
    private String useYn;

    /**
     * 공통코드 참조 (업무구분: NC)
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
    private String categoryGroup = "C0002";  // 업무구분 그룹 코드

    @Column(name = "CATEGORY_CD")
    @Builder.Default
    private String categoryValue = "NC";     // 업무구분 코드 값

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiagnosticQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DiagnosticScoreLevel> scoreLevels = new ArrayList<>();

}

