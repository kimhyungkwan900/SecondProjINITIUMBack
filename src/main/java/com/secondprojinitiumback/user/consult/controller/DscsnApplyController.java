package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.requestdto.DscsnApplyRequestDto;
import com.secondprojinitiumback.user.consult.service.DscsnApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consult")
public class DscsnApplyController {
    private final DscsnApplyService dscsnApplyService;

    //--- 상담 신청
    @PostMapping("/new")
    public ResponseEntity<?> dscsnApply(@ModelAttribute DscsnApplyRequestDto dscsnApplyRequestDto) {
        try{
            dscsnApplyService.applyConsultation(dscsnApplyRequestDto);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("신청 중 문제가 발생했습니다.");
        }
    }
}
