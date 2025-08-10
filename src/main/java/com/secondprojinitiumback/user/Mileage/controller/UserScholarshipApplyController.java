package com.secondprojinitiumback.user.Mileage.controller;

import com.secondprojinitiumback.common.domain.CommonCode;
import com.secondprojinitiumback.common.repository.CommonCodeRepository;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipApplyRequestDto;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipUserInfoDto;
import com.secondprojinitiumback.user.Mileage.service.UserScholarshipApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/scholarship")
@RequiredArgsConstructor
public class UserScholarshipApplyController {

    private final UserScholarshipApplyService applyService;
    private final CommonCodeRepository codeRepository;

    // 사용자 기본 정보 + 마일리지 점수 조회
    @GetMapping("/info/{studentNo}")
    public UserScholarshipUserInfoDto getInfo(@PathVariable String studentNo) {
        return applyService.getUserInfo(studentNo);
    }

    // 은행 코드 목록 조회 (은행명 드롭다운용)
    @GetMapping("/banks")
    public List<CommonCode> getBanks() {
        return codeRepository.findAllById_CodeGroup("BK0001");
    }

    // 장학금 신청
    @PostMapping("/apply")
    public void apply(@RequestBody UserScholarshipApplyRequestDto dto) {
        applyService.apply(dto);
    }
}

