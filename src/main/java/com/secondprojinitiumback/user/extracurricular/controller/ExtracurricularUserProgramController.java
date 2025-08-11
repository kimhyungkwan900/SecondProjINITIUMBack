package com.secondprojinitiumback.user.extracurricular.controller;

import com.secondprojinitiumback.user.extracurricular.dto.AppliedExtracurricularProgramDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ApplyProgramDTO;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularProgramDTO;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularProgramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/extracurricular")
public class ExtracurricularUserProgramController {

    private final ExtracurricularProgramUserService extracurricularProgramUserService;
    // 프로그램 리스트
    @GetMapping("/program/list")
    public ResponseEntity<Page<ExtracurricularProgramDTO>> filterPrograms(
            @RequestParam(required = false) List<Integer> competencyIds,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String statusFilter,
            @PageableDefault(size = 5, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ExtracurricularProgramDTO> result = extracurricularProgramUserService.findByFilters(competencyIds, keyword, statusFilter,pageable);
        return ResponseEntity.ok(result);
    }
    // 프로그램 디테일
    @GetMapping("/program")
    public ResponseEntity<ExtracurricularProgramDTO> programDetail(
            @RequestParam(required = false) Long eduMngId
    ){
        ExtracurricularProgramDTO dto = extracurricularProgramUserService.findProgramByEduMngId(eduMngId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/program/applied")
    @PreAuthorize("hasAnyRole('S', 'E', 'A')")
    public ResponseEntity<Page<AppliedExtracurricularProgramDTO>> getAppliedPrograms(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 5, sort = "eduAplyId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        String userId = userDetails.getUsername();
        Page<AppliedExtracurricularProgramDTO> result = extracurricularProgramUserService.getAppliedPrograms(userId, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/program/mylist")
    public ResponseEntity<Page<ApplyProgramDTO>> getMyProgramList(
            @RequestParam String stdfntNo,
            @RequestParam(required = false) String eduFnshYn,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // 필수 파라미터 체크
        if (stdfntNo == null) {
            return ResponseEntity.badRequest().build();
        }

        Page<ApplyProgramDTO> page = extracurricularProgramUserService.getApplyPrograms(
                stdfntNo, eduFnshYn, keyword, pageable);

        return ResponseEntity.ok(page);
    }



}
