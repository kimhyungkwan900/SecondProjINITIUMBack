package com.secondprojinitiumback.user.employee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeStatusId implements Serializable {
    @Column(name = "EMP_STTS_CD_SE", length = 6)
    private String employeeStatusCodeSe;

    @Column(name = "EMP_STTS_CD", length = 10)
    private String employeeStatusCode;

}