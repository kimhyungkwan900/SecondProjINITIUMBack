package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularSurveyRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularSurveyResponse;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import com.secondprojinitiumback.user.student.domain.Student;
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
        dto.setSrvyId(srvyId);
        dto.setSrvyRspnsDt(LocalDateTime.now());

        ExtracurricularSurveyResponse surveyResponse = new ExtracurricularSurveyResponse();

        // DB에서 설문조사 엔티티를 반드시 조회
        ExtracurricularSurvey survey = extracurricularSurveyRepository.findById(srvyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 설문조사가 존재하지 않습니다. ID: " + srvyId));
        surveyResponse.setExtracurricularSurvey(survey);

        // Student 엔티티 생성 및 세팅 (혹은 조회)
        Student student = Student.builder()
                .studentNo(dto.getStdntNo())
                .build();
        surveyResponse.setStudent(student);

        surveyResponse.setSurveyResponseContent(dto.getSrvyRspnsCn());
        surveyResponse.setSurveyResponseDate(dto.getSrvyRspnsDt());
        surveyResponse.setSrvyDgstfnScr(dto.getSrvyDgstfnScr());

        extracurricularSurveyResponseRepository.save(surveyResponse);

        Long eduMngId = surveyResponse.getExtracurricularSurvey().getExtracurricularProgram().getEduMngId();
        String studentNo = surveyResponse.getStudent().getStudentNo();

        extracurricularCompletionService.autoCompleteExtracurricularProgram(eduMngId, studentNo);
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
