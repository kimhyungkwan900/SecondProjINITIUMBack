package com.secondprojinitiumback.user.student.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.user.student.constant.StudentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STDNT_STTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentStatusInfo {

    @Id
    @Column(name = "STDNT_STTS_CD", length = 10)
    private String statusCode;

    @Column(name = "CD_SE", length = 20)
    private String codeGroup = "CO0002";

    @Column(name = "CD", length = 20)
    private String code = "SL";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CD_SE", referencedColumnName = "CD_SE", insertable = false, updatable = false),
            @JoinColumn(name = "CD", referencedColumnName = "CD", insertable = false, updatable = false)
    })
    private CommonCode commonCode;

    @Column(name = "STDNT_STTS_NM", length = 50, nullable = false)
    private String statusName;

    @Column(name = "RMK")
    private String remark;

    public StudentStatus getStatusEnum() {
        return StudentStatus.fromCode(this.statusCode);
    }
}