package com.secondprojinitiumback.user.consult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnInfoDto {

    private String dscsnInfoId; //상담일정 ID

    private String dscsnStatus; //상담 상태

    private String dscsnResultCn; //상담결과 내용

    private String dscsnReleaseYn; //상담결과 공개여부

    private DscsnApplyDto dscsnApply;  //신청서 ID
}
