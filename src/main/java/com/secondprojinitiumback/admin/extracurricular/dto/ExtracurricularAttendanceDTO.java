package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularAttendanceDTO {
    private Long atndcId; // 출석 ID
    private ExtracurricularSchedule extracurricularSchedule;
    private Student student; // 학생 정보
}
