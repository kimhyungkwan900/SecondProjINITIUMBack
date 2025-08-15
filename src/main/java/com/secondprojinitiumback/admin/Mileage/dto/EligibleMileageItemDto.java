package com.secondprojinitiumback.admin.Mileage.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EligibleMileageItemDto {
    private Long id;         // mileage item id
    private String itemCode; // 항목 코드
    private String eduNm;    // 비교과명
    private Integer eduMlg;  // 기본 마일리지
    private boolean granted; // 이미 적립했는지
}
