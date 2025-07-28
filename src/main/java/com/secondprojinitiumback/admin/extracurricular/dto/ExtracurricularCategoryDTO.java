package com.secondprojinitiumback.admin.extracurricular.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularCategoryDTO {
    private Long categoryId;
    private Long stgrId;
    private String ctgryNm;
    private Date dataCrtDt;
    private String ctgrtUseYn;


}
