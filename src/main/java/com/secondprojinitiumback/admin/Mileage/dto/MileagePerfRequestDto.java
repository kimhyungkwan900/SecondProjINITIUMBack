package com.secondprojinitiumback.admin.Mileage.dto;

import lombok.Data;

@Data
public class MileagePerfRequestDto {

    private String studentNo;       // 학번
    private Long mileageItemId;     // 마일리지 항목 ID
    private Long scorePolicyId;     // 배점 정책 ID
    private Long scholarshipApplyId; // 장학금 신청 ID (선택)

}
