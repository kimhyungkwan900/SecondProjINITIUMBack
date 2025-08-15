package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAdminSurveyResponseDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularSurveyRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularSurveyResponse;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
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
            throw new CustomException(ErrorCode.PROGRAM_NOT_FOUND);
        }
        return modelMapper.map(survey, ExtracurricularSurveyDTO.class);
    }

    //비교과 프로그램에 응답한 설문 확인 리스트
    public Page<ExtracurricularAdminSurveyResponseDTO> getSurveyResponse(Long eduMngId, Pageable pageable) {
        return extracurricularSurveyResponseRepository
                .findExtracurricularSurveyResponsesByExtracurricularSurvey_ExtracurricularProgram_EduMngId(eduMngId, pageable)
                .map(response -> ExtracurricularAdminSurveyResponseDTO.builder()
                        .srvyRspnsId(response.getSrvyRspnsId()) // 응답 ID
                        .srvyDgstfnScr(response.getSrvyDgstfnScr()) // 만족도 점수
                        .studentNo(response.getStudent().getStudentNo()) // 학번
                        .name(response.getStudent().getName()) // 이름
                        .surveyResponseContent(response.getSurveyResponseContent()) // 설문 응답 내용
                        .build()
                );
    }
}

