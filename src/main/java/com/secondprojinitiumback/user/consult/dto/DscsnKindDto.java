package com.secondprojinitiumback.user.consult.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnKindDto {

    private String dscsnKindId; //상담항목 코드

    private String dscsnKindName;//상담 항목명

    private String dscsnTypeName;//상담 유형(지도교수 상담, 진로취업 상담, 심리상담, 학습상담)

}
