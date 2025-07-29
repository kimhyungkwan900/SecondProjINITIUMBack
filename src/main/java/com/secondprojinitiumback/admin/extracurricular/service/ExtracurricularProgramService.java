package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.*;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfo;
import com.secondprojinitiumback.admin.extracurricular.domain.test.EmpInfoRepository;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramFormDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramUpdateFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularCategoryRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExtracurricularProgramService {

    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final ExtracurricularCategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final EmpInfoRepository empInfoRepository;

    public void insertExtracurricularProgram(ExtracurricularProgramFormDTO dto, String empId){
        EmpInfo empInfo = empInfoRepository.findById(empId)
                .orElseThrow(() -> new IllegalArgumentException("직원이 존재하지 않습니다."));

        ExtracurricularProgram program = ExtracurricularProgram.builder()
                .empInfo(empInfo) // 사원 ID 필요
                .extracurricularCategory(dto.getExtracurricularCategory()) // 마찬가지로 필요
                .eduNm(dto.getEduNm())
                .eduType(dto.getEduType())
                .eduTrgtLmt(dto.getEduTrgtLmt())
                .eduGndrLmt(dto.getEduGndrLmt())
                .eduSlctnType(dto.getEduSlctnType())
                .eduPtcpNope(dto.getEduPtcpNope())
                .eduPrps(dto.getEduPrps())
                .eduDtlCn(dto.getEduDtlCn())
                .eduAplyBgngDt(dto.getEduAplyBgngDt())
                .eduAplyEndDt(dto.getEduAplyEndDt())
                .eduBgngYmd(dto.getEduBgngYmd())
                .eduEndYmd(dto.getEduEndYmd())
                .eduPlcNm(dto.getEduPlcNm())
                .eduAplyDt(LocalDateTime.now())
                .sttsNm(SttsNm.REQUESTED) // 상태 ENUM
                .build();

        extracurricularProgramRepository.save(program);
    }

    public void updateExtracurricularProgram(ExtracurricularProgramUpdateFormDTO dto) {
        ExtracurricularProgram program = extracurricularProgramRepository.findById(dto.getEduMngId()).orElseThrow();

        program.setSttsNm(dto.getSttsNm());
        program.setEduMlg(dto.getEduMlg());
        program.setSttsChgDt(LocalDateTime.now());

        extracurricularProgramRepository.save(program);
    }
}
