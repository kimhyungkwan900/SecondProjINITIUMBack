package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularAttendanceDTO {
    private Long atndcId; // 출석 ID
    private ExtracurricularSchedule extracurricularSchedule;
    private StdntInfo stdntInfo; // 학생 정보
}
