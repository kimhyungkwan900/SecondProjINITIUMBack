package com.secondprojinitiumback.user.diagnostic.domain;

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

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    @Builder.Default
    private List<DiagnosticQuestion> questions = new ArrayList<>();
}

