package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import com.secondprojinitiumback.admin.Mileage.domain.ScorePolicy;
import com.secondprojinitiumback.admin.Mileage.dto.*;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.Mileage.repository.ScorePolicyRepository;
import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularProgram;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularProgramRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScorePolicyService {

    private final ScorePolicyRepository scorePolicyRepository;
    private final ExtracurricularProgramRepository programRepository;
    private final MileageItemRepository mileageItemRepository;

    // 1. 목록 조회 (검색 + 페이징)
    public PageResponseDto<ScorePolicyResponseDto> getList(
            PageRequestDto requestDto, String eduNm) {
        Pageable pageable = requestDto.toPageable();

        Page<ScorePolicy> page = scorePolicyRepository.searchByEduNm(eduNm, pageable);

        List<ScorePolicyResponseDto> dtoList = page.getContent().stream()
                .map(ScorePolicyResponseDto::from)
                .collect(Collectors.toList());

        return PageResponseDto.<ScorePolicyResponseDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(requestDto)
                .totalCount(page.getTotalElements())
                .build();
    }

    // 2. 등록
    public ScorePolicyResponseDto register(ScorePolicyRequestDto dto) {

        //비교과 프로그램 id로 실제 객체 가져오기
        ExtracurricularProgram program = programRepository.findById(dto.getEduMngId())
                .orElseThrow(() -> new RuntimeException("비교과 프로그램이 없습니다"));

        // 마일리지 항목 id로 실제 객체 가져오기
        MileageItem item = mileageItemRepository.findById(dto.getMileageItemId())
                .orElseThrow(() -> new RuntimeException("해당 마일리지 항목이 존재하지 않습니다"));

        // build 메서드를 사용하여 ScorePolicy 객체 생성
        ScorePolicy policy = ScorePolicy.builder()
                .scoreCriteria(dto.getScoreCriteria())
                .requiredAttendance(dto.getRequiredAttendance())
                .scoreRate(dto.getScoreRate())
                .useYn(dto.getUseYn())
                .createdAt(LocalDateTime.now())
                .program(program)
                .mileageItem(item)
                .build();

        //db에 저장하고 응답 DTO로 변환
        return ScorePolicyResponseDto.from(scorePolicyRepository.save(policy));
    }

    // 3. 상세 조회
    public ScorePolicyResponseDto findById(Long id) {
        ScorePolicy policy = scorePolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정책을 찾을 수 없습니다."));
        return ScorePolicyResponseDto.from(policy);
    }

    //4. 삭제
    public void deleteAll(List<Long> ids) {
        if (ids.size() == 1) {
            //1개만 삭제 하는 경우
            scorePolicyRepository.deleteById(ids.get(0));
        } else {
            //여러개 삭제 하는 경우
            scorePolicyRepository.deleteAllByIdInBatch(ids);
        }
    }
}

