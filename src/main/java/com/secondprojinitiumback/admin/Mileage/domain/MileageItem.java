package com.secondprojinitiumback.admin.Mileage.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.expression.spel.ast.NullLiteral;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "MLG_ALTCL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MileageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MLG_ALTCL_ID")
    private Long id; //마일리지 항목 ID(FK)

    @Column(name="MLG_ALTCL_CD", nullable = false, length = 20)
    private String itemCode; //마일리지 항목 코드

    @Column(name="CRTN_DT")
    private LocalDateTime createdAt; //생성일

    @Column(name="MDFCN_DT")
    private LocalDateTime modifiedAt; //수정일


    @OneToMany(mappedBy = "mileageItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ScorePolicy> scorePolicies; //배점 정책

    //비교과 프로그램 외래키로 연결 (조회만 가능하도록)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EDU_MNG_ID", nullable = false)
    private ExtracurricularProgram program; //비교과 프로그램 ID


}
