package com.secondprojinitiumback.admin.extracurricular.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularCategoryFormDTO {
   private Long ctgryId;
   private Long stgrId;
   private String ctgryNm;
   private Date dataCrtDt;
   private String ctgryUseYn;

}
