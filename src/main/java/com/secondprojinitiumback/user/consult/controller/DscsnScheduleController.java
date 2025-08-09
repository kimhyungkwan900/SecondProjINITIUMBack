package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnScheduleRequestDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnScheduleResponseDto;
import com.secondprojinitiumback.user.consult.service.DscsnScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DscsnScheduleController {
    private final DscsnScheduleService dscsnScheduleService;

    //--- 일정 등록
    @PostMapping("/api/consult/schedule/{dscsnType}")
    public ResponseEntity<?> createSchedule(@ModelAttribute DscsnScheduleRequestDto dscsnScheduleRequestDto, @PathVariable String dscsnType) {
        try{
            dscsnScheduleService.saveDscsnSchedule(dscsnScheduleRequestDto, dscsnType);
            return ResponseEntity.ok("일정 등록 완료");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 등록 중 문제가 발생했습니다.");
        }
    }

    //--- 일정 조회
    @GetMapping({"/api/consult/schedule"})
    public ResponseEntity<?> getSchedule(@RequestParam String dscsnType, @RequestParam String empNo) {
        ZoneId zone = ZoneId.of("Asia/Seoul"); // 필요 시 시스템 기본: ZoneId.systemDefault()
        LocalDate today = LocalDate.now(zone);

        LocalDate sunDay = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        String startDay = sunDay.format(DateTimeFormatter.BASIC_ISO_DATE);

        LocalDate target  = sunDay.plusDays(14);
        String endDay = target.format(DateTimeFormatter.BASIC_ISO_DATE);

        List<DscsnScheduleResponseDto> dscsnSchedules = dscsnScheduleService.getDscsnSchedule(startDay, endDay, dscsnType, empNo);

        return ResponseEntity.ok(dscsnSchedules);
    }

    //--- 일정 삭제
    @DeleteMapping("/api/consult/schedule/{scheduleId}")
    public ResponseEntity<?> deleteSchedule(@PathVariable String scheduleId) {
        try {
            dscsnScheduleService.deleteDscsnSchedule(scheduleId);
            return ResponseEntity.ok("일정 삭제 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("일정 삭제 중 문제가 발생했습니다.");
        }
    }
}
