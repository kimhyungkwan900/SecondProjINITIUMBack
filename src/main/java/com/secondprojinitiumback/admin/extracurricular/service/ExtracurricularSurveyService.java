package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularSurveyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtracurricularSurveyService {

    private final ExtracurricularSurveyRepository extracurricularSurveyRepository;
    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final ModelMapper modelMapper;

    // 비교과 프로그램 설문 조사 등록
    public void insertSurvey(Long eduMngId, ExtracurricularSurveyFormDTO dto){
        // 중복 설문 등록 체크
        boolean exists = extracurricularSurveyRepository.existsByExtracurricularProgram_EduMngId(eduMngId);
        if (exists) {
            throw new IllegalStateException("해당 프로그램에 대한 설문이 이미 존재합니다.");
        }
        // 프로그램 ID를 통해 ExtracurricularProgram 객체를 조회
        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId).orElseThrow();
        dto.setExtracurricularProgram(program);
        // DTO를 ExtracurricularSurvey로 변환 후 저장
        extracurricularSurveyRepository.save(modelMapper.map(dto, ExtracurricularSurvey.class));
    }

    public ExtracurricularSurveyDTO getSurvey(Long eduMngId) {
        // 아이디를 통해 ExtracurricularSurvey 객체를 조회 (없을 경우 예외 발생)
        ExtracurricularSurvey survey = extracurricularSurveyRepository
                .findByExtracurricularProgram_EduMngId(eduMngId);
        if (survey == null) {
            throw new IllegalArgumentException("해당 프로그램의 설문이 존재하지 않습니다. eduMngId = " + eduMngId);
        }
        // ExtracurricularSurvey를 DTO로 변환하여 반환
        return modelMapper.map(survey, ExtracurricularSurveyDTO.class);
    }

    // 비교과 프로그램 설문 조사 삭제
    public void deleteSurvey(Long srvyRspnsId) {
        // 아이디를 통해 ExtracurricularSurvey 객체를 조회
        ExtracurricularSurvey survey = extracurricularSurveyRepository.findById(srvyRspnsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 설문 조사가 존재하지 않습니다. id=" + srvyRspnsId));
        // ExtracurricularSurvey 삭제
        extracurricularSurveyRepository.delete(survey);
    }
}
