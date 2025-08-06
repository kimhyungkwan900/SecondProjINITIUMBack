package com.secondprojinitiumback.user.extracurricular.controller;

import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularProgramDTO;
import com.secondprojinitiumback.user.extracurricular.service.ExtracurricularProgramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/program")
    public ResponseEntity<ExtracurricularProgramDTO> programDetail(
            @RequestParam(required = false) Long eduMngId
    ){
        ExtracurricularProgramDTO dto = extracurricularProgramUserService.findProgramByEduMngId(eduMngId);
        return ResponseEntity.ok(dto);
    }
}
