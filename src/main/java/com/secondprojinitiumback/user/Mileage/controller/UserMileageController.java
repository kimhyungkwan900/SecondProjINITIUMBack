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

    @GetMapping("/summary")
    public UserMileageSummaryDto getMileageSummary(
            @RequestParam String studentNo,
            PageRequestDto pageRequestDto
    ) {
        return userMileageHistoryService.getMileageSummary(studentNo, pageRequestDto);
    }
}

