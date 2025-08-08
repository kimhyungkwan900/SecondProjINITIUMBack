package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.common.DscsnSatisfactionDto;
import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnApplyRequestDto;
import com.secondprojinitiumback.user.consult.service.DscsnSatisfactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DscsnSatisfactionController {
    private final DscsnSatisfactionService dscsnSatisfactionService;

    //--- 상담 만족도 등록
    @PostMapping("/api/consult/satisfaction")
    public ResponseEntity<?> newSatisfaction(@RequestBody DscsnSatisfactionDto dscsnSatisfactionDto) {
        try{
            dscsnSatisfactionService.saveDscsnSatisfaction(dscsnSatisfactionDto);
            return ResponseEntity.ok("만족도 등록 완료");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("만족도 설문 등록 중 에러 발생");
        }
    }
}
