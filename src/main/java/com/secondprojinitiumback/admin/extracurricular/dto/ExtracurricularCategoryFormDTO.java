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

   public void ChanageCtrUseYn(String ctgryUseYn) {
      this.ctgryUseYn = ctgryUseYn;
   }
   // 생성일시를 현재 시간으로 설정
   public void createDataCrtDt() {
      this.dataCrtDt = LocalDateTime.now();
   }

}
