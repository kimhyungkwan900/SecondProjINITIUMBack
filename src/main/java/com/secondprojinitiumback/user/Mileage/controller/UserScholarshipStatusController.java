package com.secondprojinitiumback.user.Mileage.controller;


import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipStatusDto;
import com.secondprojinitiumback.user.Mileage.service.UserScholarshipStatusService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/scholarship/status")
public class UserScholarshipStatusController {

    private final UserScholarshipStatusService statusService;

    @GetMapping("/{studentNo}")
    public PageResponseDto<UserScholarshipStatusDto> getStatus(
            @PathVariable String studentNo,
            PageRequestDto pageRequestDto
    ) {
        return statusService.getMyScholarshipStatus(studentNo, pageRequestDto);
    }
}

