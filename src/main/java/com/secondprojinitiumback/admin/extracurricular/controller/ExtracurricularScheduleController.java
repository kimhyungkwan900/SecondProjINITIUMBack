package com.secondprojinitiumback.admin.extracurricular.controller;


import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularScheduleDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/extracurricular/schedule")
@RequiredArgsConstructor
public class ExtracurricularScheduleController {

    private final ExtracurricularScheduleService extracurricularScheduleService;

    // 특정 비교과 프로그램 일정 조회
    @GetMapping("/list")
    public ResponseEntity<List<ExtracurricularScheduleDTO>> getExtracurricularSchedule(
            @RequestParam("eduMngId") Long eduMngId
    ) {
        List<ExtracurricularScheduleDTO> scheduleList = extracurricularScheduleService.getSchedulesByProgramId(eduMngId);
        return ResponseEntity.ok(scheduleList);
    }

}
