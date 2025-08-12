package com.secondprojinitiumback.user.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileagePerfRepository;
import com.secondprojinitiumback.admin.Mileage.repository.MileageTotalRepository;
import com.secondprojinitiumback.admin.Mileage.repository.ScholarshipApplyRepository;
import com.secondprojinitiumback.user.Mileage.dto.UserMileageHistoryDto;
import com.secondprojinitiumback.user.Mileage.dto.UserMileageSummaryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMileageHistoryService {

    private final MileagePerfRepository mileagePerfRepository;
    private final ScholarshipApplyRepository scholarshipApplyRepository;
    private final MileageTotalRepository mileageTotalRepository;

    public UserMileageSummaryDto getMileageSummary(String studentNo, PageRequestDto pageRequestDto) {

        // 1) 총점
        double totalScore = mileageTotalRepository.findByStudent_StudentNo(studentNo)
                .map(MileageTotal::getTotalScore)
                .orElse(0);

        Pageable pageable = pageRequestDto.toPageable();

        // 2) 전체 지급 내역 조회 (페이징 X)
        List<UserMileageHistoryDto> perfList = mileagePerfRepository
                .findAllByStudent_StudentNo(studentNo)
                .stream()
                .map(perf -> UserMileageHistoryDto.from(perf, totalScore))
                .toList();


        // 2-1) 전체 차감 내역 조회 (페이징 X)
        List<UserMileageHistoryDto> applyList = scholarshipApplyRepository
                .findAllByStudent_StudentNo(studentNo)
                .stream()
                .map(apply -> UserMileageHistoryDto.from(apply, totalScore))
                .toList();

        // 3) 합치기
        List<UserMileageHistoryDto> all = new ArrayList<>();
        all.addAll(perfList);
        all.addAll(applyList);

        // 4) 최신순 정렬
        all.sort(Comparator.comparing(UserMileageHistoryDto::getCreatedAt).reversed());

        // 5) 수동 페이징
        int currentPage = Math.max(1, pageRequestDto.getPage());
        int size = Math.max(1, pageRequestDto.getSize());

        int start = Math.min((currentPage - 1) * size, all.size());
        int end = Math.min(start + size, all.size());


        List<UserMileageHistoryDto> pageList = all.subList(start, end);

        PageResponseDto<UserMileageHistoryDto> pageResponse =
                PageResponseDto.<UserMileageHistoryDto>withAll()
                        .dtoList(pageList)
                        .pageRequestDto(pageRequestDto)
                        .totalCount(all.size())
                        .build();

        return UserMileageSummaryDto.builder()
                .totalScore(totalScore)
                .history(pageResponse)
                .build();
    }
}

