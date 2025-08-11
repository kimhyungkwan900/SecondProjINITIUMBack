package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularApplyUpdateDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularApplyAdminService;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularApplyAdminController {

        private final ExtracurricularApplyAdminService extracurricularApplyAdminService;

        @PutMapping("/admin/status/batch")
        public ResponseEntity<?> updateApplyStatusBatch(@RequestBody List<ExtracurricularApplyUpdateDTO> requestList) {
            System.out.println("test" + requestList);
            try {
                extracurricularApplyAdminService.updateExtracurricularApplyStatusBatch(requestList);
                return ResponseEntity.ok("상태가 정상적으로 변경되었습니다.");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
}
