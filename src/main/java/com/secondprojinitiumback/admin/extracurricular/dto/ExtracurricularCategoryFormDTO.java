package com.secondprojinitiumback.admin.extracurricular.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularCategoryFormDTO {
   private Long ctgryId;
   private Long stgrId;
   private String ctgryNm;
   private LocalDateTime dataCrtDt;
   private String ctgryUseYn;
}
