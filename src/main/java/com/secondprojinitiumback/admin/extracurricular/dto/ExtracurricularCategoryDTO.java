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
    private String ctgrtUseYn;


}
