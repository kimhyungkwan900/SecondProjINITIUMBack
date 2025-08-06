package com.secondprojinitiumback.common.domain;

import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "COMM_CODE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonCode{

    // 코드 그룹 ID
    @EmbeddedId
    private CommonCodeId id;

    // 코드명
    @Column(name = "CD_NM", length = 100, nullable = false)
    private String codeName;

    // 정렬 순서
    @Column(name = "SORT_ORDR")
    private Integer sortOrder;

    // 사용 여부
    @Column(name = "USE_YN", length = 1, nullable = false)
    @ColumnDefault("'Y'")
    private String useYn;

    // 비고
    @Column(name = "RMK", length = 200)
    private String remark;

    @Builder
    public CommonCode(CommonCodeId id, String codeName, Integer sortOrder, String useYn, String remark) {
        this.id = id;
        this.codeName = codeName;
        this.sortOrder = sortOrder;
        this.useYn = useYn != null ? useYn : "Y";
        this.remark = remark;
    }
}