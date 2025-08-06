package com.secondprojinitiumback.user.Mileage.dto;

import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMileageRecordDto { //사용자 지급 내역

    private String eduNm; //비교과 프로그램명
    private Integer accMlg; //지급 마일리지
    private Double totalScore; // 누적 마일리지 점수
    private LocalDateTime createdAt; // 지급일자

    // 관리자용 MileagePerf 엔티티에서 필요한 값만 추출하여 DTO로 변환
    public static UserMileageRecordDto from(MileagePerf perf, double totalScore) {
        return UserMileageRecordDto.builder()
                .eduNm(perf.getMileageItem().getProgram().getEduNm())
                .accMlg(perf.getAccMlg())
                .createdAt(perf.getCreatedAt())
                .totalScore(totalScore) // 현재 누적 마일리지를 함께 응답
                .build();
    }
}
