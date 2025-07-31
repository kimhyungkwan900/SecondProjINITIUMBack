package com.secondprojinitiumback.admin.Mileage.dto;

import lombok.Data;

@Data
public class ScorePolicyRequestDto {

    private String scoreCriteria; // 배점 조건 설명
    private Integer requiredAttendance; // 최소 출석 횟수
    private Double scoreRate; //지급 비율
    private String useYn; // 사용 여부 ('Y', 'N')
    private Long eduMngId; // 비교과 프로그램 ID (외래키)
    private Long mileageItemId; // 마일리지 항목 ID (외래키)
}
