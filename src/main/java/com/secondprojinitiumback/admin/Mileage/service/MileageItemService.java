package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageItemService {

    private final MileageItemRepository mileageItemRepository;
    private final ExtracurricularProgramRepository programRepository;

    //등록
    public MileageItemResponseDto register(MileageItemRequestDto dto){
        ExtracurricularProgram program = getProgram(dto.getEduMngId());1
    }



    //수정

    //삭제

    //조회
}
