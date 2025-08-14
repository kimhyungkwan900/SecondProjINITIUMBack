package com.secondprojinitiumback.admin.extracurricular.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularCategoryDTO {
    private Long ctgryId;
    private Long stgrId;
    private String ctgryNm;
    private LocalDateTime dataCrtDt;
    private String ctgryUseYn;

    private String ctgryDtl;

    private String subjectCode;  // SchoolSubject.subjectCode
    private String subjectName;  // SchoolSubject.subjectName

    private String subCategory; // 상위역량 이름
    private String coreCategory; // 핵심역량  이름
    private Long coreCategoryId; //핵심역량 ID;
}