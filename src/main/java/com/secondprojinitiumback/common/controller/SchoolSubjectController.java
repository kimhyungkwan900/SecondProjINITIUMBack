package com.secondprojinitiumback.common.controller;

import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.dto.response.SchoolSubjectResponse;
import com.secondprojinitiumback.common.service.SchoolSubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scsbjt")
@RequiredArgsConstructor
public class SchoolSubjectController {

    private final SchoolSubjectService schoolSubjectService;

    // 목록/검색 (페이지네이션)
    @GetMapping
    public Page<SchoolSubjectResponse> search(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String divisionCodeSe,
            @RequestParam(required = false) String divisionCode,
            Pageable pageable
    ) {
        return schoolSubjectService.search(q, divisionCodeSe, divisionCode, pageable)
                .map(SchoolSubjectResponse::from);
    }

    // 단건 조회
    @GetMapping("/{subjectCode}")
    public ResponseEntity<SchoolSubjectResponse> getOne(@PathVariable String subjectCode) {
        SchoolSubject schoolSubject = schoolSubjectService.findByCode(subjectCode);
        return ResponseEntity.ok(SchoolSubjectResponse.from(schoolSubject));
    }

    // 셀렉트박스용 경량 목록
    @GetMapping("/options")
    public Page<SchoolSubjectResponse.Option> options(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String divisionCodeSe,
            @RequestParam(required = false) String divisionCode,
            Pageable pageable
    ) {
        return schoolSubjectService.search(q, divisionCodeSe, divisionCode, pageable)
                .map(SchoolSubjectResponse.Option::from);
    }
}