package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularProgramUpdateFormDTO {
    private Long eduMngId;

    private SttsNm sttsNm;

    private int eduMlg;

    private LocalDateTime sttsChgDt;

}
