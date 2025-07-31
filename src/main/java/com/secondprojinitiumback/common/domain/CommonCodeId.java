package com.secondprojinitiumback.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class CommonCodeId implements Serializable {

    // 코드 그룹 ID
    @Column(name = "CD_SE", length = 6, nullable = false)
    private String codeGroup;

    // 코드 ID
    @Column(name = "CD", length = 10, nullable = false)
    private String code;
}