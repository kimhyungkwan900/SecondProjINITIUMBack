package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularAttendance;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularSchedule;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAttendanceDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularAttendanceRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularScheduleRepository;
import com.secondprojinitiumback.user.extracurricular.domain.ExtracurricularApply;
import com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm;
import com.secondprojinitiumback.user.extracurricular.repository.ExtracurricularApplyRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtracurricularAttendanceService {

    private final ExtracurricularAttendanceRepository extracurricularAttendanceRepository;
    private final ExtracurricularScheduleRepository extracurricularScheduleRepository;

    private final StudentRepository studentRepository;

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

            // Student 엔티티로 조회
            Student student = studentRepository.findById(stdntNo)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학생이 존재하지 않습니다."));

            // 기존 출석 조회
            Optional<ExtracurricularAttendance> existingAttendanceOpt =
                    extracurricularAttendanceRepository.findByExtracurricularScheduleAndStudent(schedule, student);

            if (existingAttendanceOpt.isPresent()) {
                // 있으면 업데이트
                ExtracurricularAttendance existingAttendance = existingAttendanceOpt.get();
                existingAttendance.setAtndcYn(isPresent ? "Y" : "N");
                existingAttendance.setAtndcDt(LocalDateTime.now());
            } else {
                // 없으면 새로 저장
                ExtracurricularAttendance attendance = ExtracurricularAttendance.builder()
                        .extracurricularSchedule(schedule)
                        .student(student)
                        .atndcDt(LocalDateTime.now())
                        .atndcYn(isPresent ? "Y" : "N")
                        .build();
                extracurricularAttendanceRepository.save(attendance);
            }
        }
    }
    // 특정 비교과 프로그램 Id로 출석 조회
    public List<ExtracurricularAttendanceDTO> getStudentsForAttendance(Long eduShdlId) {
        ExtracurricularSchedule schedule = extracurricularScheduleRepository
                .findById(eduShdlId)
                .orElseThrow(() -> new IllegalArgumentException("일정이 존재하지 않습니다."));

        Long eduMngId = schedule.getExtracurricularProgram().getEduMngId();

        // 승인된 신청 학생 리스트
        List<ExtracurricularApply> approvedApplications =
                extracurricularApplyRepository.findExtracurricularAppliesByExtracurricularProgram_EduMngIdAndAprySttsNm(
                        eduMngId, AprySttsNm.ACCEPT
                );

        // 출결 데이터 리스트 (출결이 등록된 학생)
        List<ExtracurricularAttendance> attendanceList =
                extracurricularAttendanceRepository.findByExtracurricularSchedule(schedule);

        // 승인 학생 맵 (studentNo -> Student)
        Map<String, Student> approvedStudentMap = approvedApplications.stream()
                .map(ExtracurricularApply::getStudent)
                .collect(Collectors.toMap(Student::getStudentNo, s -> s));

        // 출결 학생 맵 (studentNo -> ExtracurricularAttendance)
        Map<String, ExtracurricularAttendance> attendanceMap = attendanceList.stream()
                .collect(Collectors.toMap(
                        att -> att.getStudent().getStudentNo(),
                        att -> att
                ));

        // 두 집합 학생 번호 합집합
        Set<String> allStudentNos = new HashSet<>();
        allStudentNos.addAll(approvedStudentMap.keySet());
        allStudentNos.addAll(attendanceMap.keySet());

        List<ExtracurricularAttendanceDTO> dtoList = new ArrayList<>();

        for (String studentNo : allStudentNos) {
            Student student = approvedStudentMap.get(studentNo);
            ExtracurricularAttendance attendance = attendanceMap.get(studentNo);

            // 출결 데이터가 없으면 상태는 'U' (미처리)
            String status = (attendance != null) ? attendance.getAtndcYn() : "U";
            Long atndcId = (attendance != null) ? attendance.getAtndcId() : null;

            // 학생 객체가 없으면(출결 테이블에만 있음) 출석 학생 이름을 attendance -> student에서 가져오거나 "알 수 없음" 처리
            String studentName = (student != null) ? student.getName() :
                    (attendance != null && attendance.getStudent() != null) ? attendance.getStudent().getName() : "알 수 없음";

            dtoList.add(ExtracurricularAttendanceDTO.builder()
                    .atndcId(atndcId)
                    .studentNo(studentNo)
                    .studentName(studentName)
                    .status(status)
                    .build());
        }

        // 필요시 정렬도 가능 (예: 학생 번호 기준)
        dtoList.sort(Comparator.comparing(ExtracurricularAttendanceDTO::getStudentNo));

        return dtoList;
    }
}
