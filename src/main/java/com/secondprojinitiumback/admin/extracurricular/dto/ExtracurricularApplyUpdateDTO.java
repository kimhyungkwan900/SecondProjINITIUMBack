package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.student.domain.Student;

public class ExtracurricularApplyUpdateDTO {
    private Long eduAplyId;
    private ExtracurricularProgram extracurricularProgram; // 프로그램 정보
    private Student Student; // 학생 정보
    private String eduAplyCn; // 신청 작성 내용
    private AprySttsNm aprySttsNm; // 신청 상태
    private String eduAplyDt; // 신청 일시
}
