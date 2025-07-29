package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.dto.MileageItemRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MileageItemService {

    private final MileageItemRepository mileageItemRepository;
    private final ExtracurricularProgramRepository programRepository;

//    //목록 조회 (전체)
//    public List<MileageItemResponseDto> findAll(){
//        return mileageItemRepository.findAll().stream()
//                .map(this::toResponseDto)
//                .collect(Collectors.toList());
//    }
//
//    //조건 검색
//    public List<MileageItemResponseDto> search(String itemCode, String programName){
//        return mileageItemRepository.findAll().stream()
//                .filter(item ->
//                        (itemCode == null || item.getItemCode().equals(itemCode)) &&)
//                        (programName == null || programRepository.findById(item.getEdu
//    }

    //등록


    //삭제
}
