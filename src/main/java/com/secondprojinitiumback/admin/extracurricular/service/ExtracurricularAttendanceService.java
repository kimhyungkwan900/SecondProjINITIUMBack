package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularAttendance;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfo;
import com.secondprojinitiumback.admin.extracurricular.domain.test.StdntInfoRepository;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAttendanceDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularAttendanceRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularScheduleRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtracurricularAttendanceService {

    private final ExtracurricularAttendanceRepository extracurricularAttendanceRepository;
    private final ExtracurricularScheduleRepository extracurricularScheduleRepository;
    private final StdntInfoRepository stdntInfoRepository;
    private final ExtracurricularApplyRepository extracurricularApplyRepository;

    // 비교과 프로그램 출석 저장
    @Transactional
    public void saveAttendances(Long eduShdlId, Map<String, Boolean> attendanceMap) {
        // 일정 조회
        ExtracurricularSchedule schedule = extracurricularScheduleRepository.findById(eduShdlId)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        for (Map.Entry<String, Boolean> entry : attendanceMap.entrySet()) {
            String stdntNo = entry.getKey();
            Boolean isPresent = entry.getValue();

            StdntInfo student = stdntInfoRepository.findById(stdntNo)
                    .orElseThrow(() -> new IllegalArgumentException("학생이 존재하지 않습니다. 학번: " + stdntNo));
            // 기존 출석 조회
            Optional<ExtracurricularAttendance> existingAttendanceOpt =
                    extracurricularAttendanceRepository.findByExtracurricularScheduleAndStdntInfo(schedule, student);
            if (existingAttendanceOpt.isPresent()) {
                // 있으면 업데이트
                ExtracurricularAttendance existingAttendance = existingAttendanceOpt.get();
                existingAttendance.setAtndcYn(isPresent ? "Y" : "N");
                existingAttendance.setAtndcDt(LocalDateTime.now());
                extracurricularAttendanceRepository.save(existingAttendance);
            } else {
                // 없으면 새로 저장
                ExtracurricularAttendance attendance = ExtracurricularAttendance.builder()
                        .extracurricularSchedule(schedule)
                        .stdntInfo(student)
                        .atndcDt(LocalDateTime.now())
                        .atndcYn(isPresent ? "Y" : "N")
                        .build();
                extracurricularAttendanceRepository.save(attendance);
            }
        }
    }

    // 특정 비교과 프로그램 Id로 출석 조회
    public List<ExtracurricularAttendanceDTO> getStudentsForAttendance(Long eduShdlId) {
        // 일정 조회
        ExtracurricularSchedule schedule = extracurricularScheduleRepository
                .findById(eduShdlId)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        Long eduMngId = schedule.getExtracurricularProgram().getEduMngId();
        // 해당 프로그램에 승인 상태로 신청한 학생 리스트 조회
        List<ExtracurricularApply> approvedApplications =
                extracurricularApplyRepository.findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndAprySttsNm(eduMngId, AprySttsNm.ACCEPT);
        // 학생 정보 리스트 추출
        return approvedApplications.stream()
                .map(apply -> {
                    ExtracurricularAttendanceDTO dto = new ExtracurricularAttendanceDTO();
                    dto.setStdntInfo(apply.getStdntInfo());
                    // 필요 시 추가 매핑
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
