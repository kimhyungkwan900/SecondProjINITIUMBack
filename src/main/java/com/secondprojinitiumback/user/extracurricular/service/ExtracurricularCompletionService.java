package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularAttendanceRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularCompletion;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularCompletionRepository;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularSurveyResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExtracurricularCompletionService {

    private final ExtracurricularApplyRepository extracurricularApplyRepository;
    private final ExtracurricularAttendanceRepository extracurricularAttendanceRepository;
    private final ExtracurricularSurveyResponseRepository extracurricularSurveyResponseRepository;
    private final ExtracurricularCompletionRepository extracurricularCompletionRepository; // ✅ 추가

    private final ExtracurricularProgramRepository extracurricularProgramRepository;

    // 비교과 프로그램 자동 완료 수료 처리
    public void autoCompleteExtracurricularProgram(Long eduMngId, String stdntNo) {
        double attendanceRate = extracurricularAttendanceRepository.calculateAttendanceRate(eduMngId, stdntNo);
        boolean hasSurvey = extracurricularSurveyResponseRepository.existsByEduMngIdAndStdntNo(eduMngId, stdntNo);

        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId).orElseThrow();
        String programCndCn = program.getCndCn(); // 프로그램의 출석률 기준
        // 출석률 기준을 숫자로 변환 (예: "80%" -> 80.0)
        double attendanceThreshold;

        try {
            attendanceThreshold = Double.parseDouble(programCndCn.replace("%", "").trim());
        } catch (Exception e) {
            attendanceThreshold = 80.0; // 기본값 설정 또는 예외 재처리
        }

        if (attendanceRate >= attendanceThreshold && hasSurvey) {
            // ExtracurricularApply 조회
            ExtracurricularApply apply = extracurricularApplyRepository
                    .findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStdntInfo_StdntNo(eduMngId, stdntNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생의 신청 정보가 없습니다."));

            boolean alreadyCompleted = extracurricularCompletionRepository.existsByExtracurricularApply(apply);
            if (!alreadyCompleted) {
                ExtracurricularCompletion completion = ExtracurricularCompletion.builder()
                        .extracurricularApply(apply)
                        .eduFnshYn("Y")
                        .eduFnshDt(LocalDateTime.now())
                        .build();
                extracurricularCompletionRepository.save(completion);
            }
        }else{
            ExtracurricularApply apply = extracurricularApplyRepository
                    .findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStdntInfo_StdntNo(eduMngId, stdntNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생의 신청 정보가 없습니다."));

            boolean alreadyCompleted = extracurricularCompletionRepository.existsByExtracurricularApply(apply);
            if (!alreadyCompleted) {
                ExtracurricularCompletion completion = ExtracurricularCompletion.builder()
                        .extracurricularApply(apply)
                        .eduFnshYn("N")
                        .eduFnshDt(LocalDateTime.now())
                        .build();
                extracurricularCompletionRepository.save(completion);
            }
        }
    }
}