package com.secondprojinitiumback.user.consult.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="DSCSN_KND")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DscsnKind {

    @Id
    @Column(name = "DSCSN_KND_ID", nullable = false, length = 4)
    private String dscsnKindId; //상담항목 코드

    @Column(name = "DSCSN_KND_NM", nullable = false, length = 10)
    private String dscsnKindName;//상담 항목명

    @Column(name = "DSCSN_TYPE_NM", nullable = false, length = 10)
    private String dscsnTypeName;//상담 유형
}
