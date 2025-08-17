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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/extracurricular")
public class ExtracurricularUserProgramController {

    private final ExtracurricularProgramUserService extracurricularProgramUserService;

    // === 프로그램 리스트 ===
    @GetMapping("/program/list")
    public ResponseEntity<Page<ExtracurricularProgramDTO>> filterPrograms(
            @RequestParam(required = false) List<Integer> competencyIds,   // 그대로 유지
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String statusFilter,
            @PageableDefault(size = 5, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ExtracurricularProgramDTO> result =
                extracurricularProgramUserService.findByFilters(competencyIds, keyword, statusFilter, pageable);
        return ResponseEntity.ok(result);
    }

    // === 프로그램 디테일 ===
    @GetMapping("/program")
    public ResponseEntity<ExtracurricularProgramDTO> programDetail(
            @RequestParam(required = false) Long eduMngId
    ){
        ExtracurricularProgramDTO dto = extracurricularProgramUserService.findProgramByEduMngId(eduMngId);
        return ResponseEntity.ok(dto);
    }

    // === 내가 신청한 ===
    @GetMapping("/program/applied")
    @PreAuthorize("hasAnyRole('S', 'E', 'A')")
    public ResponseEntity<Page<AppliedExtracurricularProgramDTO>> getAppliedPrograms(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @PageableDefault(size = 5, sort = "eduAplyId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        String userId = userDetails.getUsername();
        Page<AppliedExtracurricularProgramDTO> result =
                extracurricularProgramUserService.getAppliedPrograms(userId, pageable);
        return ResponseEntity.ok(result);
    }

    // === 내 프로그램 목록 ===
    @GetMapping("/program/mylist")
    public ResponseEntity<Page<ApplyProgramDTO>> getMyProgramList(
            @RequestParam String stdfntNo,                                 // 그대로 유지
            @RequestParam(required = false) String eduFnshYn,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        if (stdfntNo == null) return ResponseEntity.badRequest().build();
        Page<ApplyProgramDTO> page =
                extracurricularProgramUserService.getApplyPrograms(stdfntNo, eduFnshYn, keyword, pageable);
        return ResponseEntity.ok(page);
    }


    // 리스트 alias: /programs
    @GetMapping("/programs")
    public ResponseEntity<Page<ExtracurricularProgramDTO>> listProgramsAlias(
            @RequestParam(required = false) List<Long> competencyIds,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String statusFilter,
            @PageableDefault(size = 12, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        // 서비스는 기존 시그니처(List<Integer>)를 쓰고 있으므로 변환해서 재사용
        List<Integer> asInteger = (competencyIds == null) ? null
                : competencyIds.stream().map(Long::intValue).toList();

        Page<ExtracurricularProgramDTO> result =
                extracurricularProgramUserService.findByFilters(asInteger, keyword, statusFilter, pageable);
        return ResponseEntity.ok(result);
    }

    // 디테일 alias: /programs/{eduMngId}
    @GetMapping("/programs/{eduMngId}")
    public ResponseEntity<ExtracurricularProgramDTO> programDetailAlias(@PathVariable Long eduMngId) {
        return ResponseEntity.ok(extracurricularProgramUserService.findProgramByEduMngId(eduMngId));
    }

    // 내 프로그램 목록 alias: 토큰 기반(me)
    @GetMapping("/programs/me")
    @PreAuthorize("hasAnyRole('S','E','A')")
    public ResponseEntity<Page<ApplyProgramDTO>> myProgramsAlias(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @RequestParam(required = false) String eduFnshYn,
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 10, sort = "eduMngId", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        String stdntNo = userDetails.getUsername();
        Page<ApplyProgramDTO> page =
                extracurricularProgramUserService.getApplyPrograms(stdntNo, eduFnshYn, keyword, pageable);
        return ResponseEntity.ok(page);
    }
}
