package com.secondprojinitiumback.user.consult.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DscsnInfoSearchDto {

    String userType; //사용자 유형(S(학생), E(교직원), A(역량센터))

    String serialNo; //학번 아니면 사번

    String dscsnType; //상담분야

    String startDate; //상담일: 시작

    String endDate; //상담일: 종료

    String dscsnStatus; //상담상태

    String year; //연도

    String startMonth; //학기
    String endMonth;

    String dscsnKindId; //상담항목

    String studentNo; //학번

    String studentName; // /성명

    String studentStatus; //학적상태

    //상담자(아이디): 일단 보류

    String depart; //소속(학과)
}
