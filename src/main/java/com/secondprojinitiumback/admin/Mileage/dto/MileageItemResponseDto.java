package com.secondprojinitiumback.admin.Mileage.dto;


import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class MileageItemResponseDto {

    private Long id; //마일리지 항목 ID(FK)
    private String itemCode; //마일리지 항목 코드
    private LocalDateTime createdAt; //생성일
    private LocalDateTime modifiedAt; //수정일

    private Long eduMngId; //비교과 프로그램 ID
    private String eduNm; // 비교과 프로그램명
    private Integer eduMlg; //마일리지
    private List<ScorePolicyResponseDto> scorePolicies; //배점 정책 목록

    //entity -> dto 변환
    public static MileageItemResponseDto from(MileageItem item) {
        return MileageItemResponseDto.builder()
                .id(item.getId())
                .itemCode(item.getItemCode())
                .createdAt(item.getCreatedAt())
                .modifiedAt(item.getModifiedAt())
                .eduMngId(item.getProgram().getEduMngId())
                .eduNm(item.getProgram().getEduNm())
                .eduMlg(item.getProgram().getEduMlg())
                .scorePolicies(item.getScorePolicies() != null ? item.getScorePolicies().stream()
                                .map(ScorePolicyResponseDto::from)
                                .toList() : List.of())
                .build();
    }
}
