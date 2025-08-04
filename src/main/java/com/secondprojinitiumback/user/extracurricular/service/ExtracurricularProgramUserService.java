package com.secondprojinitiumback.user.extracurricular.service;

import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import com.secondprojinitiumback.user.extracurricular.dto.ExtracurricularProgramDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtracurricularProgramUserService {

    private final ExtracurricularProgramRepository extracurricularProgramRepository;
    private final ModelMapper modelMapper;
    //사용자 Extracurricular 목록 조회
    public List<ExtracurricularProgramDTO> getExtracurricularList() {

        return modelMapper.map(extracurricularProgramRepository.findAll(), List.class);

    }



}
