package com.secondprojinitiumback.user.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMileageTotalDto { //사용자 누적 점수

    private String studentNo; //학번
    private Double totalScore; //누적 마일리지 점수

    public static UserMileageTotalDto from(MileageTotal entity) {
        return UserMileageTotalDto.builder()
                .studentNo(entity.getStudentNo())
                .totalScore(entity.getTotalScore())
                .build();
    }
}
