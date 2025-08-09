package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.MileageItemResponseDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MileageItemService {

    private final MileageItemRepository mileageItemRepository;
    private final ExtracurricularProgramRepository programRepository;

    //1. 전체 목록 조회 (검색 + 페이징)
    public PageResponseDto<MileageItemResponseDto> getList(
            PageRequestDto requestDto, String itemCode, String eduNm) {
          Pageable pageable = requestDto.toPageable();

        Page<MileageItem> page = mileageItemRepository.searchWithPagingAndPolicies(itemCode, eduNm, pageable);

        List<MileageItemResponseDto> dtoList = page.getContent().stream()
                .map(MileageItemResponseDto::from)
                .collect(Collectors.toList());

        return PageResponseDto.<MileageItemResponseDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(requestDto)
                .totalCount(page.getTotalElements())
                .build();
    }

    //2. 마일리지 항목 등록
    public MileageItemResponseDto register(MileageItemRequestDto dto){

        //비교과 프로그램 id로 객체 찾기
        ExtracurricularProgram program = programRepository.findById(dto.getEduMngId())
                .orElseThrow(()-> new RuntimeException("비교과 프로그램이 존재하지 않습니다"));

        //마일리지 항목 생성
        MileageItem mileageItem = MileageItem.builder()
                .itemCode(dto.getItemCode())
                .createdAt(LocalDateTime.now())
                .program(program)
                .build();

        //마일리지 항목 저장 후 dto로 변환
        return MileageItemResponseDto.from(mileageItemRepository.save(mileageItem));
    }

    // 3. 마일리지 항목 상세 보기
    public MileageItemResponseDto findById(Long id) {
        MileageItem item = mileageItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("마일리지 항목이 존재하지 않습니다."));
        return MileageItemResponseDto.from(item);
    }


    // 4. 항목 여러 개 삭제 (선택 삭제)
    public void deleteAll(List<Long> ids) {

        mileageItemRepository.deleteAllByIdInBatch(ids); // 한번에 일괄 삭제
    }


}
