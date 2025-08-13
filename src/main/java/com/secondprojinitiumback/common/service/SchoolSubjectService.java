package com.secondprojinitiumback.common.service;

import com.secondprojinitiumback.common.domain.SchoolSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SchoolSubjectService {
    Page<SchoolSubject> search(String q, String divisionCodeSe, String divisionCode, Pageable pageable);
    Optional<SchoolSubject> findByCode(String subjectCode);
}