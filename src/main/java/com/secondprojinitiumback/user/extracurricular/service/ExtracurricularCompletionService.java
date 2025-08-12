package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularAttendanceRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularScheduleRepository;
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
    private final ExtracurricularCompletionRepository extracurricularCompletionRepository;
    private final ExtracurricularScheduleRepository extracurricularScheduleRepository;
    private final ExtracurricularProgramRepository extracurricularProgramRepository;

    // 비교과 프로그램 자동 완료 수료 처리
    public void autoCompleteExtracurricularProgram(Long eduMngId, String studentNo) {
        double attendanceRate = calculateAttendanceRate(eduMngId, studentNo);
        System.out.println(attendanceRate);

        boolean hasSurvey = extracurricularSurveyResponseRepository.existsByEduMngIdAndStudent(eduMngId, studentNo);

        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId)
                .orElseThrow(() -> new IllegalArgumentException("프로그램을 찾을 수 없습니다."));

        String programCndCn = program.getCndCn();
        double attendanceThreshold;

        try {
            attendanceThreshold = Double.parseDouble(programCndCn.replace("%", "").trim());
        } catch (Exception e) {
            attendanceThreshold = 80.0; // 기본값
        }

        ExtracurricularApply apply = extracurricularApplyRepository
                .findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStudent_studentNo(eduMngId, studentNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생의 신청 정보가 없습니다."));

        boolean alreadyCompleted = extracurricularCompletionRepository.existsByExtracurricularApply(apply);

        if (!alreadyCompleted) {
            ExtracurricularCompletion completion = ExtracurricularCompletion.builder()
                    .extracurricularApply(apply)
                    .eduFnshYn(attendanceRate >= attendanceThreshold && hasSurvey ? "Y" : "N")
                    .eduFnshDt(LocalDateTime.now())
                    .build();
            extracurricularCompletionRepository.save(completion);
        }
    }

    public double calculateAttendanceRate(Long eduMngId, String studentNo) {
        int totalAttendance = extracurricularScheduleRepository
                .countExtracurricularSchedulesByExtracurricularProgram_EduMngId(eduMngId);

        int myAttendance = extracurricularAttendanceRepository
                .countByExtracurricularSchedule_ExtracurricularProgram_EduMngIdAndStudent_StudentNoAndAtndcYn(
                        eduMngId, studentNo, "Y"
                );

        if (totalAttendance == 0) {
            return 0.0;
        }

        return (double) myAttendance / totalAttendance * 100;
    }
}