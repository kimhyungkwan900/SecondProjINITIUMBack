package com.secondprojinitiumback.common.domain; // 적절한 패키지 경로로 변경 권장

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;


@Entity
@Table(name = "SCSBJT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolSubject {

    @Id
    @Column(name = "SCSBJT_NO", length = 5)
    private String subjectCode;

    @Column(name = "SCSBJT_NM", length = 20, nullable = false, unique = true)
    private String subjectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "DEPT_DIV_CD", referencedColumnName = "CD"),
            @JoinColumn(name = "DEPT_DIV_GRP", referencedColumnName = "CD_SE")
    })
    private CommonCode deptDivision;

    // 학과구분코드
    @Column(name = "DEPT_DIV_GRP", length = 20, insertable = false, updatable = false)
    private String deptDivisionGroup = "CO0003";

    public static SchoolSubject of(String code, String name, CommonCode division) {
        SchoolSubject s = new SchoolSubject();
        s.subjectCode = code;
        s.subjectName = name;
        s.deptDivision = division;
        return s;
    }
}