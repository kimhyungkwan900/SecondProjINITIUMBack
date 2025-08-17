package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSurvey;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAdminSurveyResponseDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularSurveyParticipationStatusDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularSurveyRepository;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularSurveyResponse;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularSurveyResponseDTO;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtracurricularSurveyResponseService {

    private final ExtracurricularSurveyRepository extracurricularSurveyRepository;
    private final ExtracurricularSurveyResponseRepository extracurricularSurveyResponseRepository;
    private final ExtracurricularProgramRepository extracurricularProgramRepository; // 추가
    private final ExtracurricularApplyRepository extracurricularApplyRepository; // 추가
    private final ModelMapper modelMapper;
    private final ExtracurricularCompletionService extracurricularCompletionService;

    // 비교과 프로그램 설문조사 응답 저장
    @Transactional
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
    @Transactional(readOnly = true)
    public ExtracurricularSurveyDTO getSurvey(Long eduMngId) {
        ExtracurricularSurvey survey = extracurricularSurveyRepository
                .findByExtracurricularProgram_EduMngId(eduMngId);

        if (survey == null) {
            throw new CustomException(ErrorCode.PROGRAM_NOT_FOUND);
        }
        return modelMapper.map(survey, ExtracurricularSurveyDTO.class);
    }

    //비교과 프로그램에 응답한 설문 확인 리스트
    @Transactional(readOnly = true)
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

    /**
     * 특정 비교과 프로그램의 설문조사 참여 현황을 반환합니다.
     * @param eduMngId 교육 관리 ID
     * @return ExtracurricularSurveyParticipationStatusDTO 참여 현황 DTO
     */
    @Transactional(readOnly = true)
    public ExtracurricularSurveyParticipationStatusDTO getSurveyParticipationStatus(Long eduMngId) {
        // 1. ExtracurricularProgram 정보 조회
        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId)
                .orElseThrow(() -> new CustomException(ErrorCode.PROGRAM_NOT_FOUND));

        // 2. ExtracurricularSurvey 정보 조회
        ExtracurricularSurvey survey = extracurricularSurveyRepository.findByExtracurricularProgram_EduMngId(eduMngId);
        if (survey == null) {
            throw new CustomException(ErrorCode.SURVEY_NOT_FOUND); // 해당 프로그램에 연결된 설문이 없는 경우
        }

        // 3. 프로그램 신청(승인) 학생 수 조회 (totalApplicants)
        // ExtracurricularApply 엔티티의 aprySttsNm 필드를 사용하여 '승인' 상태의 학생만 카운트
        int totalApplicants = extracurricularApplyRepository.countByExtracurricularProgram_EduMngIdAndAprySttsNm(eduMngId, AprySttsNm.ACCEPT);

        // 4. 설문 응답 완료 학생 수 조회 (totalResponded)
        // ExtracurricularSurveyResponse 엔티티에서 해당 설문에 응답한 고유 학생 수 카운트
        int totalResponded = extracurricularSurveyResponseRepository.countDistinctStudentByExtracurricularSurvey_SrvyId(survey.getSrvyId());

        // 5. 참여율 계산
        double participationRate = 0.0;
        if (totalApplicants > 0) {
            participationRate = (double) totalResponded / totalApplicants * 100.0;
        }

        return ExtracurricularSurveyParticipationStatusDTO.builder()
                .eduMngId(program.getEduMngId())
                .programName(program.getEduNm())
                .empNo(program.getEmployee() != null ? program.getEmployee().getEmpNo() : null) // 담당 교직원 empNo
                .surveyTitle(survey.getSrvyTtl())
                .totalApplicants(totalApplicants)
                .totalResponded(totalResponded)
                .participationRate(participationRate)
                .build();
    }
}

