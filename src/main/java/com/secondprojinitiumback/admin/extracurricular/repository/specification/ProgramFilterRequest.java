package com.secondprojinitiumback.admin.extracurricular.repository.specification;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramFilterRequest {
    private SttsNm status;
    private String programName;
    private String departmentCode;
}
