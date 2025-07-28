package com.secondprojinitiumback.admin.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExtracurricularProgramService {
    private final ExtracurricularProgramRepository extracurricularProgramRepository;

}
