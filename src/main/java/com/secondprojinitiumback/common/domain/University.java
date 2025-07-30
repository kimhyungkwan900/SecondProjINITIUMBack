package com.secondprojinitiumback.common.domain;

import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*; // Lombok import 추가

@Entity
@Table(name = "UNIV")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class University extends BaseEntity {
    @Id
    @Column(name = "UNIV_CD", length = 8)
    private String universityCode;

    @Column(name = "UNIV_NM", length = 200, nullable = false)
    private String universityName;
}