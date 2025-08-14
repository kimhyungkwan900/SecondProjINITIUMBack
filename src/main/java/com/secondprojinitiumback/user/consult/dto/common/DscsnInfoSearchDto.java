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

    String userType;        //사용자 유형(S(학생), E(교직원), A(역량센터))
    String consultorType;   // 상담자 유형 (P(교수), K(외부상담사)

    String empNo;        //사번

    String dscsnType;       //상담분야

    String startDate;       //상담일: 시작

    String endDate;         //상담일: 종료

    String dscsnStatus;     //상담상태

    String year;            //연도

    String startMonth;      //학기
    String endMonth;

    String dscsnKindId;     //상담항목

    String studentNo;       //학번

    String name;     //성명

    String studentStatus;   //학적상태

    String subjectCode;          //학과코드
}
