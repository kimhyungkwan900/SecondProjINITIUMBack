package com.secondprojinitiumback.user.consult.dto;

import com.secondprojinitiumback.user.consult.domain.Dscsn_dt;
import com.secondprojinitiumback.user.consult.domain.Dscsn_knd;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dscsn_apply_dto {

    private String dscsn_apply_id;

    private String student_telno;

    private String dscsn_apply_cn;

    private String dscsn_status;

    private Student student;

    private Dscsn_dt dscsn_dt;

    private Dscsn_knd dscsn_knd;
}
