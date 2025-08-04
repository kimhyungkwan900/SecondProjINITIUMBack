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
    private final ExtracurricularCompletionRepository extracurricularCompletionRepository;

    private final ExtracurricularProgramRepository extracurricularProgramRepository;

    // 비교과 프로그램 자동 완료 수료 처리
    public void autoCompleteExtracurricularProgram(Long eduMngId, String studentNo) {
        // 출석률 계산 (0.0 ~ 1.0 사이 실수)
        double attendanceRate = extracurricularAttendanceRepository.calculateAttendanceRate(eduMngId, studentNo);

        // 설문 응답 여부 확인
        boolean hasSurvey = extracurricularSurveyResponseRepository.existsByEduMngIdAndStudent(eduMngId, studentNo);

        // 비교과 프로그램 정보 조회
        ExtracurricularProgram program = extracurricularProgramRepository.findById(eduMngId).orElseThrow();

        // 프로그램의 출석률 기준 문자열 가져오기 (예: "80%")
        String programCndCn = program.getCndCn();

        // 출석률 기준을 double로 파싱 (예: "80%" → 80.0)
        double attendanceThreshold;
        try {
            attendanceThreshold = Double.parseDouble(programCndCn.replace("%", "").trim());
        } catch (Exception e) {
            attendanceThreshold = 80.0; // 파싱 실패 시 기본값 설정
        }

        // 수료 조건: 출석률이 기준 이상 && 설문 응답 완료
        if (attendanceRate >= attendanceThreshold && hasSurvey) {
            // 신청 정보 조회
            ExtracurricularApply apply = extracurricularApplyRepository
                    .findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStudent_studentNo(eduMngId, studentNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생의 신청 정보가 없습니다."));

            // 이미 수료 처리되었는지 확인
            boolean alreadyCompleted = extracurricularCompletionRepository.existsByExtracurricularApply(apply);

            // 미처리 상태라면 수료 처리
            if (!alreadyCompleted) {
                ExtracurricularCompletion completion = ExtracurricularCompletion.builder()
                        .extracurricularApply(apply)
                        .eduFnshYn("Y") // 수료 여부: Y(수료)
                        .eduFnshDt(LocalDateTime.now()) // 수료 처리 일시
                        .build();
                extracurricularCompletionRepository.save(completion);
            }

        } else {
            // 수료 조건을 만족하지 못한 경우 (미수료 처리)
            ExtracurricularApply apply = extracurricularApplyRepository
                    .findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndStudent_studentNo(eduMngId, studentNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생의 신청 정보가 없습니다."));

            // 이미 수료 처리되었는지 확인
            boolean alreadyCompleted = extracurricularCompletionRepository.existsByExtracurricularApply(apply);

            // 미처리 상태라면 미수료 처리
            if (!alreadyCompleted) {
                ExtracurricularCompletion completion = ExtracurricularCompletion.builder()
                        .extracurricularApply(apply)
                        .eduFnshYn("N") // 수료 여부: N(미수료)
                        .eduFnshDt(LocalDateTime.now()) // 수료 처리 일시
                        .build();
                extracurricularCompletionRepository.save(completion);
            }
        }
    }
}