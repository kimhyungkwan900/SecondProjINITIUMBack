package com.secondprojinitiumback.admin.extracurricular.controller;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.EduType;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramAdminDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramFormDTO;
import com.secondprojinitiumback.admin.extracurricular.dto.ExtracurricularProgramUpdateFormDTO;
import com.secondprojinitiumback.admin.extracurricular.repository.specification.ProgramFilterRequest;
import com.secondprojinitiumback.admin.extracurricular.service.ExtracurricularProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/api/admin/extracurricular")
@RequiredArgsConstructor
public class ExtracurricularProgramAdminController {
        public final ExtracurricularProgramService extracurricularProgramService;

        // 프로그램 등록 신청
        @PostMapping(value = "/application", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> registerProgram(@RequestPart("ExtracurricularFormDTO") ExtracurricularProgramFormDTO dto,
                                                 @RequestPart(value = "image", required = false ) MultipartFile imageFile,
                                                 @RequestParam String empId) {
                System.out.println(imageFile.getName());
                try {
                        extracurricularProgramService.insertExtracurricularProgram(dto, empId, imageFile);
                        return ResponseEntity.ok("비교과 프로그램이 성공적으로 등록되었습니다.");
                } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록 중 오류 발생");
                }
        }

        // 비교과 프로그램 상태 수정 (승인/반려)
        @PutMapping("/program/update")
        public ResponseEntity<?> updateProgramStatus(@RequestBody ExtracurricularProgramUpdateFormDTO dto) {
                try {
                        extracurricularProgramService.updateExtracurricularProgram(dto);
                        return ResponseEntity.ok("비교과 프로그램 상태가 성공적으로 수정되었습니다.");
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("수정 중 오류 발생: " + e.getMessage());
                }
        }


        // 비교과 프로그램 목록 조회
        @GetMapping("/program/list")
        public ResponseEntity<Page<ExtracurricularProgramAdminDTO>> filterPrograms(
                @RequestParam(required = false) String keyword,
                @RequestParam(required = false) SttsNm status,
                @RequestParam(required = false) String departmentCode,
                @RequestParam(required = false) EduType eduType,
                @PageableDefault(size = 5, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
        ) {
                ProgramFilterRequest filter = new ProgramFilterRequest(status, keyword, departmentCode, eduType);
                Page<ExtracurricularProgramAdminDTO> result = extracurricularProgramService.filterList(filter, pageable);
                return ResponseEntity.ok(result);
        }



}
