package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtracurricularSurveyResponseAdminService {

    private final ExtracurricularSurveyResponseRepository extracurricularSurveyResponseRepository;
    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final ModelMapper modelMapper;

    public List<ExtracurricularSurveyResponseDTO> getSurveyResponsesByProgramId(Long eduMngId) {
        // 프로그램 ID로 ExtracurricularProgram 조회
        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId).orElseThrow();
        // 프로그램 ID로 설문 응답 조회
        return extracurricularSurveyResponseRepository.findExtracurricularSurveyResponseByExtracurricularSurvey_ExtracurricularProgram(program).stream()
                .map(response -> modelMapper.map(response, ExtracurricularSurveyResponseDTO.class))
                .toList();
    }
}
