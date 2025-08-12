package com.secondprojinitiumback.user.Mileage.dto;

import lombok.*;

import java.util.List;

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

    private String bankCode;    // 선택된 은행 코드
    private String bankName;    // 선택된 은행 이름
    private List<BankItem> banks; // 드롭다운용 은행 목록

    @Getter
    @AllArgsConstructor
    public static class BankItem {
        private String code; // BK 코드 (ex. 004)
        private String name; // 은행명 (ex. KB국민은행)
    }
}

