package com.secondprojinitiumback.admin.Mileage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MileageItemRequestDto {
    private String itemCode; //마일리지 항목코드
    private Long eduMngId; // 비교과 프로그램 ID
}
