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

    @Column(name = "CD_SE", length = 6, nullable = false)
    private String codeGroup;

    @Column(name = "CD", length = 10, nullable = false)
    private String code;
}