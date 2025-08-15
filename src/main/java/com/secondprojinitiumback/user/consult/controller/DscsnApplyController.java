package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnApplyRequestDto;
import com.secondprojinitiumback.user.consult.service.DscsnApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consult/apply")
public class DscsnApplyController {
    private final DscsnApplyService dscsnApplyService;

    //--- 상담 신청
    @PostMapping("/new")
    public ResponseEntity<?> dscsnApply(@RequestBody DscsnApplyRequestDto dscsnApplyRequestDto) {
        try{
            dscsnApplyService.applyConsultation(dscsnApplyRequestDto);
            return ResponseEntity.ok("상담 신청 완료");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 중 문제가 발생했습니다.");
        }
    }

    //--- 상담신청 취소
    @PostMapping("/cancel/{dscsnInfoId}")
    public ResponseEntity<?> dscsnCancel(@PathVariable(name = "dscsnInfoId") String dscsnApplyId) {
        try{
            dscsnApplyService.cancelConsultation(dscsnApplyId);
            return ResponseEntity.ok("상담 취소 완료");
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(Map.of("message", ex.getReason()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 취소 중 문제가 발생했습니다.");
        }
    }
}
