package com.secondprojinitiumback.user.student.domain;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STDNT_STTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentStatusInfo {

    @EmbeddedId
    private StudentStatusId id;

    @Column(name = "STDNT_STTS_NM", length = 50, nullable = false)
    private String studentStatusName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "STDNT_STTS_CD_SE", referencedColumnName = "CD_SE", insertable = false, updatable = false),
            @JoinColumn(name = "STDNT_STTS_CD", referencedColumnName = "CD", insertable = false, updatable = false)
    })
    private CommonCode statusCode;
}