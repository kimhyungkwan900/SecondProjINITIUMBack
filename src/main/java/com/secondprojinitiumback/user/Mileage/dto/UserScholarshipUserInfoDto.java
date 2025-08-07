package com.secondprojinitiumback.user.Mileage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserScholarshipUserInfoDto {
    private String name; // 학생이름
    private String studentNo; // 학번
    private String subjectName; //학과 이름
    private int totalScore; // 현재 마일리지 점수
    private String accountNo; // 계좌번호
}

