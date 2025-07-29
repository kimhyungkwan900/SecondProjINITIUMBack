package com.secondprojinitiumback.user.consult.dto;

import com.secondprojinitiumback.user.consult.domain.DscsnDate;
import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DscsnApplyDto {

    private String dscsn_apply_id;

    private String student_telno;

    private String dscsn_apply_cn;

    private String dscsn_status;

    private Student student;

    private DscsnDate dscsn_dt;

    private DscsnKind dscsn_kind;
}
