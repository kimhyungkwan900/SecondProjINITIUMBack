package com.secondprojinitiumback.user.consult.controller;

import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoListDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnResultDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnInfoResponseDto;
import com.secondprojinitiumback.user.consult.service.DscsnInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/consult")
public class DscsnInfoController {
    private final DscsnInfoService dscsnInfoService;

    //--- 상담내역 조회(상담사, 학생)
    @GetMapping({"/dscsnInfo/list", "/dscsnInfo/list/{page}"})
    public ResponseEntity<?> getDscsnInfoList(@ModelAttribute DscsnInfoSearchDto dscsnInfoSearchDto,  @PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<DscsnInfoResponseDto> dscsnInfos = dscsnInfoService.getDscsnInfo(dscsnInfoSearchDto, pageable);

        DscsnInfoListDto dscsnInfoListDto = DscsnInfoListDto.builder()
                .dscsnInfos(dscsnInfos)
                .maxPage(10)
                .totalPage(dscsnInfos.getTotalPages())
                .build();

        return ResponseEntity.ok(dscsnInfoListDto);
    }

    //--- 상담상태 변경(상담사, 교수)
    @PutMapping("/dscsnInfo/list/{status}")
    public ResponseEntity<?> updateDscsnStatus(@PathVariable String status, @RequestBody String dscsnInfoId) {

        try {
            dscsnInfoService.updateDscsnStatus(dscsnInfoId, status);
            return ResponseEntity.ok("상태 수정 완료");
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("입력값 오류");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("상태 수정 중 오류 발생");
        }
    }

    //--- 상담결과 등록(상담사, 교수)
    @PostMapping("/dscsnInfo/list/result")
    public ResponseEntity<?> registerResult(@RequestBody DscsnResultDto dscsnResultDto) {

        try {
            dscsnInfoService.registerDscsnResult(dscsnResultDto);
            return ResponseEntity.ok("결과 등록 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결과 등록 중 에러 발생");
        }
    }
}
