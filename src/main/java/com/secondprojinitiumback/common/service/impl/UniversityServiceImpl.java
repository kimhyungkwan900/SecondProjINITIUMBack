package com.secondprojinitiumback.common.service.impl;

import com.secondprojinitiumback.common.domain.University;
import com.secondprojinitiumback.common.dto.response.UniversityResponse;
import com.secondprojinitiumback.common.exception.CustomException;
import com.secondprojinitiumback.common.exception.ErrorCode;
import com.secondprojinitiumback.common.repository.UniversityRepository;
import com.secondprojinitiumback.common.service.Universityservice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UniversityServiceImpl implements Universityservice {

    private final UniversityRepository repository;

    @Override
    public List<UniversityResponse> findAll() {
        return repository.findAll().stream()
                .map(u -> new UniversityResponse(
                        rtrim(u.getUniversityCode()),
                        rtrim(u.getUniversityName())
                ))
                .toList();
    }

    @Override
    public UniversityResponse findByCode(String code) {
        University u = repository.findById(code)
                .orElseThrow(() -> new CustomException(ErrorCode.UNIVERSITY_NOT_FOUND));
        return new UniversityResponse(rtrim(u.getUniversityCode()), rtrim(u.getUniversityName()));
    }

    @Override
    public String findNameByCode(String code) {
        return repository.findNameByCode(code)
                .map(this::rtrim)
                .orElseThrow(() -> new CustomException(ErrorCode.UNIVERSITY_NOT_FOUND));
    }

    // UNIV_CD가 CHAR(8)인 스키마이므로 공백 패딩 제거 안전장치
    private String rtrim(String s) {
        return s == null ? null : s.replaceAll("\\s+$", "");
    }
}
