package com.secondprojinitiumback.user.student.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import com.secondprojinitiumback.user.student.constant.StudentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STDNT_STTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentStatusInfo extends BaseEntity {

    @Id
    @Column(name = "STDNT_STTS_CD", length = 10)
    private String studentStatusCode;

    @Column(name = "STDNT_STTS_CD_SE", length = 10)
    private String studentStatusCodeSe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "STDNT_STTS_CD", referencedColumnName = "CD", insertable = false, updatable = false),
            @JoinColumn(name = "STDNT_STTS_CD_SE", referencedColumnName = "CD_SE", insertable = false, updatable = false)
    })
    private CommonCode statusCode;

    @Column(name = "STDNT_STTS_NM", length = 50, nullable = false)
    private String studentStatusName;

    @Column(name = "RMK")
    private String remark;

    public StudentStatus getStatusEnum() {
        return StudentStatus.fromCode(this.studentStatusCode);
    }
}