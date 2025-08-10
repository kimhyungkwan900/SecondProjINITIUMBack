package com.secondprojinitiumback.user.Mileage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScholarshipApplyRequestDto {

    private String studentNo; // 학번
    private String accountNo; //신청할때 사용할 계좌 번호
}

