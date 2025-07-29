package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfoRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyFormDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExtracurricularApplyService {

    private final ExtracurricularApplyRepository extracurricularApplyRepository;

    private final ExtracurricularProgramRepository extracurricularProgramRepository;

    private final StdntInfoRepository stuntInfoRepository;

    // 비교과 프로그램 신청
    public void applyExtracurricular(String stdntNo, ExtracurricularApplyFormDTO dto) {
        StdntInfo stdntInfo = stuntInfoRepository.findById(stdntNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생 없음: " + stdntNo));

        dto.setEduAplyDt(LocalDateTime.now());

        // ✅ 프로그램 ID는 따로 받아야 함
        ExtracurricularProgram program = extracurricularProgramRepository.findById(dto.getEduMngId())
                .orElseThrow(() -> new IllegalArgumentException("해당 비교과 프로그램 없음: " + dto.getEduMngId()));

        ExtracurricularApply apply = ExtracurricularApply.builder()
                .stdntInfo(stdntInfo)
                .extracurricularProgram(program)
                .eduAplyCn(dto.getEduAplyCn())
                .eduAplyDt(dto.getEduAplyDt())
                .build();

        extracurricularApplyRepository.save(apply);
    }
}
