package com.secondprojinitiumback.admin.Mileage.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 조회용 DTO
public class MileageItemResponseDto {
    private Long id; //마일리지 항목 ID(FK)
    private String itemCode; //마일리지 항목 코드
    private LocalDateTime createAt; //생성일
    private LocalDateTime modifiedAt; //수정일
    private Long eduMngId; //비교과 프로그램 ID
    private String eduProgramName; // 프로그램명 (출력용)
}
