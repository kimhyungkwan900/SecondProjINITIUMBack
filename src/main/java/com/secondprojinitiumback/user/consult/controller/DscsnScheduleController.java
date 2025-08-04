package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.common.DscsnScheduleListDto;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnScheduleRequestDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnScheduleResponseDto;
import com.secondprojinitiumback.user.consult.service.DscsnScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping({"/api/consult/schedule", "/api/consult/schedule/{page}"})
    public ResponseEntity<?> getSchedule(@RequestParam String empNo, @PathVariable int page) {

        Pageable pageable = PageRequest.of(0, 10);
        Page<DscsnScheduleResponseDto> dscsnSchedules = dscsnScheduleService.getDscsnSchedule(empNo, pageable);

        DscsnScheduleListDto dscsnScheduleListDto = DscsnScheduleListDto.builder()
                .dscsnSchedules(dscsnSchedules)
                .maxPage(10)
                .totalPage(dscsnSchedules.getTotalPages())
                .build();

        return ResponseEntity.ok(dscsnScheduleListDto);
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
