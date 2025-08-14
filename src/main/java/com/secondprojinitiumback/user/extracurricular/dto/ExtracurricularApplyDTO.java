package com.secondprojinitiumback.user.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularApplyDTO {
    private Long eduAplyId;
    private String eduAplyCn; // 신청 작성 내용
    private AprySttsNm aprySttsNm; // 신청 상태
    private LocalDateTime eduAplyDt;

    private String programNm; //프로그램 이름
    private String categoryNm;//분류이름
}
