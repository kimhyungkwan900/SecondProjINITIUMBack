package com.secondprojinitiumback.user.employee.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import com.secondprojinitiumback.user.employee.constant.EmployeeStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EMP_STTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeStatusInfo extends BaseEntity {

    @Id
    @Column(name = "EMP_STTS_CD", length = 10)
    private String employeeStatusCode;

    @Column(name = "EMP_STTS_CD_SE", length = 10)
    private String employeeStatusCodeSe;

    // 공통 코드(상태그룹) 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "EMP_STTS_CD", referencedColumnName = "CD", insertable = false, updatable = false),
            @JoinColumn(name = "EMP_STTS_CD_SE", referencedColumnName = "CD_SE", insertable = false, updatable = false)
    })
    private CommonCode employeeStatusGroup;

    @Column(name = "EMP_STTS_NM", length = 50, nullable = false)
    private String employeeStatusName;

    @Column(name = "RMK")
    private String remark;

    public EmployeeStatus getStatusCode() {
        return EmployeeStatus.fromCode(this.employeeStatusCode);
    }
}
