package com.secondprojinitiumback.user.student.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class StudentStatusId implements Serializable {
    @Column(name = "STDNT_STTS_CD_SE", length = 10)
    private String studentStatusCodeSe;

    @Column(name = "STDNT_STTS_CD", length = 10)
    private String studentStatusCode;
}