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

    /** 🔹 공통코드(NC, CP 등 업무구분 코드) 참조 **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CD_SE", referencedColumnName = "CD_SE"),
            @JoinColumn(name = "CD", referencedColumnName = "CD")
    })
    private CommonCode categoryCode;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    @Builder.Default
    private List<DiagnosticQuestion> questions = new ArrayList<>();
}

