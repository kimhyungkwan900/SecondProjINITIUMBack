package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularSurveyRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularSurveyResponse;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExtracurricularSurveyResponseService {

    private final ExtracurricularSurveyRepository extracurricularSurveyRepository;
    private final ExtracurricularSurveyResponseRepository extracurricularSurveyResponseRepository;
    private final ModelMapper modelMapper;
    private final ExtracurricularCompletionService extracurricularCompletionService;

    // 비교과 프로그램 설문조사 응답 저장
    public void saveSurveyResponse(Long srvyId, ExtracurricularSurveyResponseDTO dto) {
        // 설문 ID로 설문 정보 조회 후 DTO에 설정
        dto.setExtracurricularSurvey(
                extracurricularSurveyRepository.findById(srvyId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 설문조사가 존재하지 않습니다. ID: " + srvyId))
        );
        // 설문 응답 시간 설정 (현재 시간)
        dto.setSrvyRspnsDt(LocalDateTime.now());
        // DTO를 실제 엔티티로 변환
        ExtracurricularSurveyResponse surveyResponse = modelMapper.map(dto, ExtracurricularSurveyResponse.class);
        // 설문 응답 저장
        extracurricularSurveyResponseRepository.save(surveyResponse);
        // 설문 응답 후 자동 수료 처리 호출
        Long eduMngId = surveyResponse.getExtracurricularSurvey().getExtracurricularProgram().getEduMngId(); // 비교과 프로그램 ID
        String stdntNo = surveyResponse.getStdntInfo().getStdntNo(); // 학생 번호
        // 출석률 및 설문 기준 충족 시 자동 수료 처리
        extracurricularCompletionService.autoCompleteExtracurricularProgram(eduMngId, stdntNo);
    }

    // 비교과 프로그램에 등록된 설문 조사 확인
    public ExtracurricularSurveyDTO getSurvey(Long eduMngId) {
        ExtracurricularSurvey survey = extracurricularSurveyRepository
                .findByExtracurricularProgram_EduMngId(eduMngId);

        if (survey == null) {
            throw new IllegalArgumentException("해당 프로그램에 등록된 설문이 없습니다.");
        }
        return modelMapper.map(survey, ExtracurricularSurveyDTO.class);
    }
}
