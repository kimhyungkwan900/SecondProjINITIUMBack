package com.secondprojinitiumback.admin.Mileage.controller;

import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.dto.ScorePolicyRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.ScorePolicyResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.ScorePolicyRepository;
import com.secondprojinitiumback.admin.Mileage.service.ScorePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/score-policies")
public class ScorePolicyController {

    private final ScorePolicyService scorePolicyService;
    private final ScorePolicyRepository scorePolicyRepository;

    // 목록 조회
    @GetMapping("/list")
    public PageResponseDto<ScorePolicyResponseDto> list(
            PageRequestDto pageRequestDto,
            @RequestParam(required = false) String eduNm
    ) {
        return scorePolicyService.getList(pageRequestDto, eduNm);
    }

    // 등록
    @PostMapping("/create")
    public ResponseEntity<ScorePolicyResponseDto> create(@RequestBody ScorePolicyRequestDto dto) {
        return ResponseEntity.ok(scorePolicyService.register(dto));
    }

    // 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScorePolicyResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(scorePolicyService.findById(id));
    }

    //삭제
    @PostMapping("/delete")
    public ResponseEntity<Void> deletePolicies(@RequestBody List<Long> ids) {
        scorePolicyService.deleteAll(ids);
        return ResponseEntity.noContent().build();
    }

    // 프론트에서 항목만 선택하고 정책 UI 없이 자동으로 ID를 가져가기 위한 경량 API
    @GetMapping("/base")
    public ResponseEntity<Long> getBasePolicyId(@RequestParam Long itemId) {
        Long id = scorePolicyRepository.findFirstActiveIdByItemId(itemId);
        if (id == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(id);
    }
}

