package com.secondprojinitiumback.common.controller;

import com.secondprojinitiumback.common.dto.response.UniversityNameResponse;
import com.secondprojinitiumback.common.dto.response.UniversityResponse;
import com.secondprojinitiumback.common.service.Universityservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/univ")
@RequiredArgsConstructor
public class UniversityController {

    private final Universityservice universityService;

    // UniversitySelect()에서 사용할 목록 조회
    @GetMapping
    public List<UniversityResponse> list() {
        return universityService.findAll();
    }

    // 코드로 단건 조회 (코드+이름)
    @GetMapping("/{univCd}")
    public UniversityResponse get(@PathVariable String univCd) {
        return universityService.findByCode(univCd);
    }

    // 코드로 이름만 조회 (요청하신 형태)
    @GetMapping(value = "/{univCd}/name")
    public UniversityNameResponse getName(@PathVariable String univCd) {
        String name = universityService.findNameByCode(univCd);
        return new UniversityNameResponse(name);
    }
}