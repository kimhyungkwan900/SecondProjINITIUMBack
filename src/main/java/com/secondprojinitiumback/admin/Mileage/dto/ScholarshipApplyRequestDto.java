package com.secondprojinitiumback.admin.Mileage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScholarshipApplyRequestDto {

    private String studentNo;       // 학번
    private String accountNo;       // 계좌번호

    private Long mileageScore;      // 누적 마일리지 점수
    private String applyStatusCode; // 신청 상태 코드 (예: 신청, 승인, 반려 등)

    private String code;            // 장학금 코드
    private String codeSe;          // 코드구분 (장학금 코드인지, 기타인지 구분)

    // 선택사항: 반려사유, 지급금액 등은 처음 신청할 땐 안 넣을 수도 있음
}

