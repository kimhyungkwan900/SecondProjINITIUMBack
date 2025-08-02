package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAttendanceDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/extracurricuar/attendance")
@RequiredArgsConstructor
public class ExtracurricularAttendanceController {

    private final ExtracurricularAttendanceService extracurricularAttendanceService;

    // 비교과 프로그램 출석 해야하는 학생 조회
    public ResponseEntity<List<ExtracurricularAttendanceDTO>> getAllAttendance(
            @RequestParam("eduShdlId") Long eduShdlId
    ) {
        extracurricularAttendanceService.getStudentsForAttendance(eduShdlId);
        return ResponseEntity.ok(extracurricularAttendanceService.getStudentsForAttendance(eduShdlId));
    }

    // 비교과 프로그램 출석 저장
    @PutMapping("/save")
    public ResponseEntity<String> saveAttendance(
            @RequestParam("eduShdlId") Long eduShdlId,
            @RequestBody Map<String, Boolean> attendanceList
            ) {
        extracurricularAttendanceService.saveAttendances(eduShdlId, attendanceList);
        return ResponseEntity.ok("출석이 저장되었습니다.");
    }

}
