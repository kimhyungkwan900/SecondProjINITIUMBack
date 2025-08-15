package com.secondprojinitiumback.user.coreCompetency.dto;

import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularProgramDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecommendProgramDto {

    private Long programId;
    private String eduNm;
    private Long subCategoryId;
    private String studentNo;
    private String assessmentNo;
    private Integer eduMlg;
    private LocalDateTime eduAplyBgngDt;
    private LocalDateTime eduAplyEndDt;
    private LocalDate eduBgngYmd;
    private LocalDate eduEndYmd;

    private ExtracurricularProgramDTO program;
}