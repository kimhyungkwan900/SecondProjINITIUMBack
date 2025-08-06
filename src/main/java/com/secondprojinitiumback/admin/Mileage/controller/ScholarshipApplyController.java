package com.secondprojinitiumback.admin.Mileage.controller;

import com.secondprojinitiumback.admin.Mileage.dto.*;
import com.secondprojinitiumback.admin.Mileage.service.ScholarshipApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/scholarships")
public class ScholarshipApplyController {

    private final ScholarshipApplyService service;

    //목록 조회
    @GetMapping
    public PageResponseDto<ScholarshipApplyResponseDto> getList(PageRequestDto requestDto,
                                                                @RequestParam(required = false) String studentName,
                                                                @RequestParam(required = false) String studentNo,
                                                                @RequestParam(required = false) String subjectName,
                                                                @RequestParam(required = false) String stateCode) {
        return service.getList(requestDto, studentName, studentNo, subjectName, stateCode);
    }

    //상세 조회
    @GetMapping("/{id}")
    public ScholarshipApplyResponseDto getDetail(@PathVariable Long id) {

        return service.getDetail(id);
    }

    // 신청 등록
    @PostMapping
    public void register(@RequestBody ScholarshipApplyRequestDto dto) {

        service.register(dto);
    }

    // 상태 변경
    @PutMapping("/{id}/status")
    public void updateStatus(@PathVariable Long id,
                             @RequestParam String newCode) {
        service.updateStatus(id, newCode);
    }

    // 반려 사유 입력
    @PutMapping("/{id}/reject-reason")
    public void updateRejectReason(@PathVariable Long id,
                                   @RequestBody String reason) {
        service.updateRejectReason(id, reason);
    }

    // 지급 처리
    @PostMapping("/{id}/payment")
    public void processPayment(@PathVariable Long id) {
        service.processPayment(id);
    }

    // 마일리지 확인
    @GetMapping("/{studentNo}/mileage")
    public MileageTotalResponseDto getMileageTotal(@PathVariable String studentNo) {
        return service.getMileageTotal(studentNo);
    }
}

