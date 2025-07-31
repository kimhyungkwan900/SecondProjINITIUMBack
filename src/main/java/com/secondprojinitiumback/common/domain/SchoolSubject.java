package com.secondprojinitiumback.common.domain; // 적절한 패키지 경로로 변경 권장

import com.secondprojinitiumback.common.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;


@Entity
@Table(name = "SCSBJT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolSubject extends BaseEntity {

    // 학과과목코드
    @Id
    @Column(name = "SCSBJT_NO", length = 5)
    private String subjectCode;

    // 학과과목명
    @Column(name = "SCSBJT_NM", length = 20, nullable = false, unique = true)
    private String subjectName;

    // 학과구분코드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "CD", referencedColumnName = "CD"),
            @JoinColumn(name = "CD_SE", referencedColumnName = "CD_SE")
    })
    private CommonCode deptDivision;

    // 학과구분코드명
    public static SchoolSubject of(String code, String name, CommonCode division) {
        SchoolSubject s = new SchoolSubject();
        s.subjectCode = code;
        s.subjectName = name;
        s.deptDivision = division;
        return s;
    }
}