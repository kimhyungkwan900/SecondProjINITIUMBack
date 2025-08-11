package com.secondprojinitiumback.admin.extracurricular.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularAttendanceDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularAttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/extracurricular/attendance")
@RequiredArgsConstructor
public class ExtracurricularAttendanceController {

    private final ExtracurricularAttendanceService extracurricularAttendanceService;

    // 비교과 프로그램 출석 해야하는 학생 조회
    @GetMapping("/list")
    public ResponseEntity<List<ExtracurricularAttendanceDTO>> getAllAttendance(
            @RequestParam("eduShdlId") Long eduShdlId) throws Exception {
        List<ExtracurricularAttendanceDTO> attendanceList = extracurricularAttendanceService.getStudentsForAttendance(eduShdlId);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(attendanceList));  // JSON 형태로 출력
        return ResponseEntity.ok(attendanceList);
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
