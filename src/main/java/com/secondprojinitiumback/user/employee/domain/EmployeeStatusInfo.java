package com.secondprojinitiumback.user.employee.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMP_STTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeStatusInfo extends BaseEntity {

    @EmbeddedId
    private EmployeeStatusId id;

    @Column(name = "EMP_STTS_NM", length = 50, nullable = false)
    private String employeeStatusName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "EMP_STTS_CD_SE", referencedColumnName = "CD_SE", insertable = false, updatable = false),
            @JoinColumn(name = "EMP_STTS_CD", referencedColumnName = "CD", insertable = false, updatable = false)
    })
    private CommonCode employeeStatusGroup;
}
