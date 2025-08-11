package com.secondprojinitiumback.admin.extracurricular.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularAttendanceDTO {
    private Long atndcId; // 출석 ID
    private String studentNo;      // 학번
    private String studentName;    // 이름
    private String status;         // 출석 여부 (Y/N)

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime atndcDt;
}
