package com.secondprojinitiumback.test;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyFormDTO;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularApplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExtracurricularApplyTest {

    @Autowired
    private ExtracurricularApplyService extracurricularApplyService;

    @Autowired
    private ExtracurricularProgramRepository extracurricularProgramRepository;

    @Test
    @DisplayName("신청 테스트")
    public void extracurricularApplyTest() {

        String stdntNo = "S000000001";

        ExtracurricularProgram extracurricularProgram = extracurricularProgramRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Extracurricular program not found"));

        ExtracurricularApplyFormDTO dto = new ExtracurricularApplyFormDTO();

        dto.setEduAplyCn(stdntNo);
        dto.getExtracurricularProgram().setEduMngId(extracurricularProgram.getEduMngId());
        extracurricularApplyService.applyExtracurricular(stdntNo, dto);

    }
}
