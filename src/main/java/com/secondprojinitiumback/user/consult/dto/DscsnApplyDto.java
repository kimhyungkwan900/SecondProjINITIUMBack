package com.secondprojinitiumback.user.consult.dto;

import com.secondprojinitiumback.user.consult.domain.DscsnDate;
import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.student.domain.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnApplyDto {

    private String dscsnApplyId;

    private String studentTelno;

    private String dscsnApplyCn;

    private String dscsnStatus;

    private String studentNo;

    private String dscsnDtId;

    private String dscsnKindId;
}
