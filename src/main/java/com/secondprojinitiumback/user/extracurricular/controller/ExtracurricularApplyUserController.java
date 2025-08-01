package com.secondprojinitiumback.user.extracurricular.controller;

import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularApplyFormDTO;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularApplyUserController {
    private final ExtracurricularApplyService extracurricularApplyService;

    // 비교과 프로그램 신청
    @PostMapping("/apply")
    public ResponseEntity<?> applyExtracurricularProgram(
            @RequestParam("stdfntNo") String stdfntNo,
            @RequestBody ExtracurricularApplyFormDTO formDTO
            ) {
        extracurricularApplyService.applyExtracurricular(stdfntNo, formDTO);
        return ResponseEntity.ok("비교과 프로그램 신청이 완료되었습니다.");
    }

    // 비교과 프로그램 신청 취소
    @DeleteMapping("/apply/cancel")
    public ResponseEntity<?> cancelExtracurricularProgram(
            @RequestParam("eduAplyId") Long eduAplyId
    ) {
        extracurricularApplyService.cancelApplyExtracurricular(eduAplyId);
        return ResponseEntity.ok("비교과 프로그램 신청이 취소되었습니다.");
    }

    // 비교과 프로그램 신청 내역 삭제
    @PutMapping("/apply/delete")
    public ResponseEntity<?> deleteExtracurricularProgram(
            @RequestParam("eduAplyId") Long eduAplyId
    ){
        extracurricularApplyService.deleteApplyExtracurricular(eduAplyId);
        return ResponseEntity.ok("비교과 프로그램 신청 내역이 삭제되었습니다.");
    }

    // 비교과 프로그램 신청 목록 조회
    @GetMapping("/apply/list")
    public ResponseEntity<List<ExtracurricularApplyDTO>> getExtracurricularApplyList(
            @RequestParam("stdfntNo") String stdfntNo
    ) {
        List<ExtracurricularApplyDTO> list = extracurricularApplyService.findExtracurricularApplylist(stdfntNo);
        return ResponseEntity.ok(list);
    }
}
