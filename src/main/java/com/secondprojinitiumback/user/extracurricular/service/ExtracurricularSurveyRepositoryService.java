package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularSurveyRepository;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExtracurricularSurveyRepositoryService {

    private final ExtracurricularSurveyRepository extracurricularSurveyRepository;
    private final ExtracurricularSurveyResponseRepository extracurricularSurveyResponseRepository;
    private final ModelMapper modelMapper;

    // 비교과 설문조사 응답 저장
    public void saveSurveyResponse(Long srvyId, ExtracurricularSurveyResponseDTO dto) {
        dto.setExtracurricularSurvey(
                extracurricularSurveyRepository.findById(srvyId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 설문조사가 존재하지 않습니다. ID: " + srvyId))
        );
        dto.setSrvyRspnsDt(LocalDateTime.now());
        // DTO를 엔티티로 변환 후 저장
        ExtracurricularSurveyFormDTO surveyResponse = modelMapper.map(dto, ExtracurricularSurveyFormDTO.class);
    }


}
