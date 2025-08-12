package com.secondprojinitiumback.common.service;

import com.secondprojinitiumback.common.dto.response.UniversityResponse;

import java.util.List;

public interface Universityservice {
    List<UniversityResponse> findAll();

    UniversityResponse findByCode(String code);

    String findNameByCode(String code);
}
