package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtracurricularApplyUpdateDTO {
    private Long eduAplyId;
    private AprySttsNm aprySttsNm; // 신청 상태
}
