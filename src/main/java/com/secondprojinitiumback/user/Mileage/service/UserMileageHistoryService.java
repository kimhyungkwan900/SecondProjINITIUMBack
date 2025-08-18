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
    private final MileageTotalRepository mileageTotalRepository;

    public UserMileageSummaryDto getMileageSummary(String studentNo, PageRequestDto pageRequestDto) {

        // 1) 상단 총점 (없으면 0)
        double headerTotal = mileageTotalRepository.findByStudent_StudentNo(studentNo)
                .map(MileageTotal::getTotalScore)
                .orElse(0);

        // 2) 전체 히스토리 변동 → 시간순 정렬
        List<MileagePerf> perfs = mileagePerfRepository.findAllByStudent_StudentNo(studentNo);

        // 3) createdAt 오름차순(시간 흐름대로) 정렬
        perfs.sort(Comparator
                .comparing(MileagePerf::getCreatedAt)
                .thenComparing(MileagePerf::getId));

        // 4) prefix sum으로 누적 계산
        List<UserMileageHistoryDto> computed = new ArrayList<>(perfs.size());
        double running = 0d;
        for (MileagePerf perf : perfs) {
            int delta = safe(perf.getAccMlg()); // +지급 / -차감
            running += delta;

            UserMileageHistoryDto row = UserMileageHistoryDto.builder()
                    .type(delta >= 0 ? "지급" : "차감")
                    .description(perf.getMileageItem() != null
                            ? perf.getMileageItem().getProgram().getEduNm()
                            : (delta >= 0 ? "비교과 지급" : "장학금 차감"))
                    .change((delta >= 0 ? "+" : "") + delta)
                    .totalScore(running)                 // 시간순으로 누적 반영
                    .createdAt(perf.getCreatedAt())
                    .build();

            computed.add(row);
        }

        // 5) (선택) 누적 합과 headerTotal 불일치 시 보정
        //    - 운영정책상 headerTotal이 ‘정답’이면 아래 보정 on
        //    - 보정하지 않으려면 이 블록 제거
        if (!computed.isEmpty()) {
            double last = computed.get(computed.size() - 1).getTotalScore();
            double offset = headerTotal - last; // 마지막 누적을 headerTotal에 맞추는 오프셋
            if (Math.abs(offset) > 1e-9) {
                for (int i = 0; i < computed.size(); i++) {
                    UserMileageHistoryDto r = computed.get(i);
                    computed.set(i, UserMileageHistoryDto.builder()
                            .type(r.getType())
                            .description(r.getDescription())
                            .change(r.getChange())
                            .totalScore(r.getTotalScore() + offset) // 전 구간에 동일 오프셋
                            .createdAt(r.getCreatedAt())
                            .build());
                }
            }
        }

        // 6) 화면은 최신순(내림차순)으로 표시하되, 누적은 위에서 계산된 걸 그대로 사용
        computed.sort(Comparator.comparing(UserMileageHistoryDto::getCreatedAt).reversed());

        // 7) 페이지네이션은 "계산 끝난 리스트"에 적용
        int page = Math.max(1, pageRequestDto.getPage());
        int size = Math.max(1, pageRequestDto.getSize());
        int start = Math.min((page - 1) * size, computed.size());
        int end = Math.min(start + size, computed.size());
        List<UserMileageHistoryDto> slice = computed.subList(start, end);


        PageResponseDto<UserMileageHistoryDto> pageResponse =
                PageResponseDto.<UserMileageHistoryDto>withAll()
                        .dtoList(slice)
                        .pageRequestDto(pageRequestDto)
                        .totalCount(computed.size())
                        .build();

        return UserMileageSummaryDto.builder()
                .totalScore(headerTotal)      // 상단 총점
                .history(pageResponse)        // 표 데이터
                .build();
    }

    private int safe(Integer v) { return v == null ? 0 : v; }
}


