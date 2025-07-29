package com.secondprojinitiumback.test;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfoRepository;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularProgramService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ExtracurricularTest {

    @Autowired
    private ExtracurricularProgramService extracurricularProgramService;

    @Autowired
    private EmpInfoRepository empInfoRepository;

    @Autowired
    private ExtracurricularCategoryRepository extracurricularCategoryRepository;

    @Test
    @DisplayName("프로그램 등록 테스트")
    public void testInsertExtracurricularProgram() {
        String empId = "E000000001"; // 예시 사원 ID
         // 예시 카테고리 ID
        ExtracurricularProgramFormDTO dto = ExtracurricularProgramFormDTO.builder()
                .extracurricularCategory(extracurricularCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("카테고리 없음")))
                .eduNm("자기계발 프로그램")
                .eduType(EduType.TEAM)
                .eduTrgtLmt(EduTrgtLmt.ALL)
                .eduGndrLmt(EduGndrLmt.MALE)
                .eduSlctnType(EduSlctnType.FIRSTCOME)
                .eduPtcpNope(30)
                .eduPrps("학생 자율 역량 강화")
                .eduDtlCn("자기계발 및 역량 기반 교육")
                .eduAplyBgngDt(LocalDateTime.now().minusDays(1))
                .eduAplyEndDt(LocalDateTime.now().plusDays(5))
                .eduBgngYmd(LocalDateTime.now().plusDays(6))
                .eduEndYmd(LocalDateTime.now().plusDays(10))
                .eduPlcNm("복지관 3층")
                .eduAplyDt(LocalDateTime.now())
                .sttsNm(SttsNm.REQUESTED) // 상태 ENUM
                .build();

        extracurricularProgramService.insertExtracurricularProgram(dto, empId);
    }
}
