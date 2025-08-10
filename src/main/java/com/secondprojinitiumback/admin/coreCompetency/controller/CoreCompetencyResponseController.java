package com.secondprojinitiumback.admin.coreCompetency.controller;

import com.secondprojinitiumback.admin.coreCompetency.service.AdminCoreCompetencyResponseService;
import com.secondprojinitiumback.user.coreCompetency.dto.UserResponseBulkRequestDto;
import com.secondprojinitiumback.user.student.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/response")
public class CoreCompetencyResponseController {

    private final AdminCoreCompetencyResponseService adminCoreCompetencyResponseService;

    @PostMapping("/submit-by-label")
    public ResponseEntity<String> submitByLabel(
            @RequestBody UserResponseBulkRequestDto dto,
            @AuthenticationPrincipal Student student // 또는 studentService.findById() 방식
    ) {
        adminCoreCompetencyResponseService.saveResponsesByLabel(student, dto);
        return ResponseEntity.ok("응답 저장 완료");
    }

}
