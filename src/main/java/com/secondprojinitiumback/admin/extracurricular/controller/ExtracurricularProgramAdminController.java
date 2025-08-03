package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramFormDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramUpdateFormDTO;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/admin/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularProgramAdminController {
        public final ExtracurricularProgramService extracurricularProgramService;

        // 프로그램 등록 신청
        @PostMapping("/apply")
        public ResponseEntity<?> registerProgram(@RequestBody ExtracurricularProgramFormDTO dto,
                                                 @RequestParam String empId) {
                try {
                        extracurricularProgramService.insertExtracurricularProgram(dto, empId);
                        return ResponseEntity.ok("비교과 프로그램이 성공적으로 등록되었습니다.");
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록 중 오류 발생");
                }
        }

        // 비교과 프로그램 상태 수정 (승인/반려)
        @PutMapping("/status/update")
        public ResponseEntity<?> updateProgramStatus(@RequestBody ExtracurricularProgramUpdateFormDTO dto) {
                try {
                        extracurricularProgramService.updateExtracurricularProgram(dto);
                        return ResponseEntity.ok("비교과 프로그램 상태가 성공적으로 수정되었습니다.");
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("수정 중 오류 발생: " + e.getMessage());
                }
        }


}
