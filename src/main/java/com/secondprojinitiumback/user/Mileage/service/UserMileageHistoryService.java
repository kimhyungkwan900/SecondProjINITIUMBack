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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class UserMileageHistoryService {
//
//    private final MileagePerfRepository mileagePerfRepository;
//    private final ScholarshipApplyRepository scholarshipApplyRepository;
//    private final MileageTotalRepository mileageTotalRepository;
//
//    //누적 점수와 내역을 함께 조회
//    public UserMileageSummaryDto getMileageSummary(String studentNo, PageRequestDto pageRequestDto) {
//
//        // 1. 사용자의 누적 점수 조회
//        double totalScore = mileageTotalRepository.findByStudent_StudentNo(studentNo)
//                .map(MileageTotal::getTotalScore)
//                .orElse(0); //없으면 0 반환
//
//        Pageable pageable = pageRequestDto.toPageable();
//
//        // 지급 내역과 차감 내역을 각각 페이징 처리
//        Page<MileagePerf> perfPage = mileagePerfRepository.findAllByStudent_StudentNo(studentNo, pageable);
//        Page<ScholarshipApply> applyPage = scholarshipApplyRepository.findAllByStudent_StudentNo(studentNo, pageable);
//
//        // 지급 결과를 dto로 변환
//        List<UserMileageHistoryDto> perfList = perfPage.getContent().stream()
//                .map(perf -> UserMileageHistoryDto.from(perf, totalScore))
//                .collect(Collectors.toList());
//
//        // 차감 결과를 dto로 변환
//        List<UserMileageHistoryDto> applyList = applyPage.getContent().stream()
//                .map(apply -> UserMileageHistoryDto.from(apply, totalScore))
//                .collect(Collectors.toList());
//
//        // 두 리스트를 합쳐서 정렬(최신순)
//        List<UserMileageHistoryDto> all = new ArrayList<>();
//        all.addAll(perfList);
//        all.addAll(applyList);
//
//        all.sort(Comparator.comparing(UserMileageHistoryDto::getCreatedAt).reversed());
//
//        // 수동 페이징 처리
//        int start = (int) pageable.getOffset();
//        int end = Math.min(start + pageable.getPageSize(), all.size());
//        List<UserMileageHistoryDto> pageList = all.subList(start, end);
//
//        //페이지 응답 처리
//        PageResponseDto<UserMileageHistoryDto> pageResponse = PageResponseDto.<UserMileageHistoryDto>withAll()
//                .dtoList(pageList)
//                .pageRequestDto(pageRequestDto)
//                .totalCount(all.size())
//                .build();
//
//        return UserMileageSummaryDto.builder()
//                .totalScore(totalScore)
//                .history(pageResponse)
//                .build();
//    }
//}
@Service
@RequiredArgsConstructor
public class UserMileageHistoryService {

    private final MileagePerfRepository mileagePerfRepository;
    private final ScholarshipApplyRepository scholarshipApplyRepository;
    private final MileageTotalRepository mileageTotalRepository;

    public UserMileageSummaryDto getMileageSummary(String studentNo, PageRequestDto pageRequestDto) {

        // 1) 총점
        double totalScore = mileageTotalRepository.findByStudent_StudentNo(studentNo)
                .map(MileageTotal::getTotalScore)
                .orElse(0);

        // 2) 전체 조회(페이징 X)
        //   - 메서드가 없다면 findAllBy...(..., Pageable.unpaged()).getContent() 사용
        List<MileagePerf> perfAll = mileagePerfRepository
                .findAllByStudent_StudentNo(studentNo, Pageable.unpaged())
                .getContent();

        List<ScholarshipApply> appAll = scholarshipApplyRepository
                .findAllByStudent_StudentNo(studentNo, Pageable.unpaged())
                .getContent();

        // 3) DTO 변환 + 합치기
        List<UserMileageHistoryDto> all = new ArrayList<>(perfAll.size() + appAll.size());
        all.addAll(perfAll.stream()
                .map(perf -> UserMileageHistoryDto.from(perf, totalScore))
                .toList());
        all.addAll(appAll.stream()
                .map(apply -> UserMileageHistoryDto.from(apply, totalScore))
                .toList());

        // 4) 최신순 정렬
        all.sort(Comparator.comparing(UserMileageHistoryDto::getCreatedAt).reversed());

        // 5) 수동 페이징(방어 포함)
        int page = Math.max(0, pageRequestDto.getPage());   // 0-기준 페이지
        int size = Math.max(1, pageRequestDto.getSize());

        int start = Math.min(page * size, all.size());       // 범위 방어
        int end   = Math.min(start + size, all.size());

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

