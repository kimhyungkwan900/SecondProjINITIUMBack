package com.secondprojinitiumback.user.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserMileageSummaryDto {

    private Double totalScore; //총 누적 마일리지 점수
    private PageResponseDto<UserMileageHistoryDto> history; // 지급/차감 내역 리스트

}


