package com.secondprojinitiumback.admin.Mileage.controller;

import com.secondprojinitiumback.admin.Mileage.dto.*;
import com.secondprojinitiumback.admin.Mileage.service.MileagePerfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/mileage-perf")
public class MileagePerfController {

    private final MileagePerfService mileagePerfService;

    // 1. 실적 목록 조회 (검색 + 페이징)
    @GetMapping("/list")
    public PageResponseDto<MileagePerfResponseDto> list(
            PageRequestDto pageRequestDto,
            @RequestParam(required = false) String studentNo,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String subjectName
    ) {
        return mileagePerfService.getList(pageRequestDto, studentNo, studentName, subjectName);
    }

    // 2. 실적 등록
    @PostMapping("/create")
    public ResponseEntity<MileagePerfResponseDto> create(@RequestBody MileagePerfRequestDto dto) {
        return ResponseEntity.ok(mileagePerfService.register(dto));
    }

    // 3. 실적 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<MileagePerfResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mileagePerfService.findById(id));
    }

    // 4. 실적 삭제
    @PostMapping("/delete")
    public ResponseEntity<Void> deletePerformances(@RequestBody List<Long> ids) {
        mileagePerfService.deleteAll(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentNo}/eligible-items")
    public ResponseEntity<List<EligibleMileageItemDto>> eligible(@PathVariable String studentNo) {
        return ResponseEntity.ok(mileagePerfService.getEligibleItems(studentNo));
    }
}
