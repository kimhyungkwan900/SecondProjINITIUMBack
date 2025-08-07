package com.secondprojinitiumback.user.Mileage.controller;

import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.user.Mileage.dto.UserMileageSummaryDto;
import com.secondprojinitiumback.user.Mileage.service.UserMileageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/mileage")
@RequiredArgsConstructor
public class UserMileageController {

    private final UserMileageHistoryService userMileageHistoryService;

    /**
     * 마일리지 총점 및 지급/차감 내역 조회
     * @param studentNo 학번
     * @param pageRequestDto 페이지 번호 및 사이즈
     * @return UserMileageSummaryDto (총점 + 내역 리스트)
     */
    @GetMapping("/summary")
    public UserMileageSummaryDto getMileageSummary(
            @RequestParam String studentNo,
            PageRequestDto pageRequestDto
    ) {
        return userMileageHistoryService.getMileageSummary(studentNo, pageRequestDto);
    }
}

