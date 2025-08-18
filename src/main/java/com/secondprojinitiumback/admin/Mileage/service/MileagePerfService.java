package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.domain.ScorePolicy;
import com.secondprojinitiumback.admin.Mileage.dto.EligibleMileageItemDto;
import com.secondprojinitiumback.admin.Mileage.dto.MileagePerfRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.MileagePerfResponseDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.Mileage.repository.MileagePerfRepository;
import com.secondprojinitiumback.admin.Mileage.repository.MileageTotalRepository;
import com.secondprojinitiumback.admin.Mileage.repository.ScorePolicyRepository;
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularAttendanceRepository; // 출결
import com.secondprojinitiumback.admin.extracurricular.repository.ExtracurricularScheduleRepository;   // 일정
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MileagePerfService {

    private final MileagePerfRepository mileagePerfRepository;
    private final StudentRepository studentRepository;
    private final MileageItemRepository mileageItemRepository;
    private final ScorePolicyRepository scorePolicyRepository;
    private final MileageTotalRepository mileageTotalRepository;

    // 팀원 리포지토리 재사용 (수료 판정용)
    private final ExtracurricularScheduleRepository scheduleRepository;
    private final ExtracurricularAttendanceRepository attendanceRepository;

    /* ------------------------------
     * 목록 조회 (검색 + 페이징)
     * ------------------------------ */
    public PageResponseDto<MileagePerfResponseDto> getList(
            PageRequestDto requestDto, String studentNo, String studentName, String subjectName) {

        Pageable pageable = requestDto.toPageable();

        Page<MileagePerf> page = mileagePerfRepository
                .searchWithPaging(studentNo, studentName, subjectName, pageable);

        List<MileagePerfResponseDto> dtoList = page.getContent().stream()
                .map(MileagePerfResponseDto::from)
                .collect(Collectors.toList());

        return PageResponseDto.<MileagePerfResponseDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(requestDto)
                .totalCount(page.getTotalElements())
                .build();
    }

    //수료 여부 확인
    private boolean hasCompletedAllSessions(String stdNo, Long eduMngId) {
        int total = scheduleRepository
                .countExtracurricularSchedulesByExtracurricularProgram_EduMngId(eduMngId);
        if (total <= 0) return false;

        int attendedY = attendanceRepository
                .countByExtracurricularSchedule_ExtracurricularProgram_EduMngIdAndStudent_StudentNoAndAtndcYn(
                        eduMngId, stdNo, "Y"
                );
        return attendedY == total;
    }


     //실적 등록 (수료자만 허용)
    @Transactional
    public MileagePerfResponseDto register(MileagePerfRequestDto dto) {
        final String stdNo = dto.getStudentNo().trim();

        // 중복 방지: 같은 학생이 같은 항목으로 이미 적립했는지
        if (mileagePerfRepository.existsByStudent_StudentNoAndMileageItem_Id(stdNo, dto.getMileageItemId())) {
            throw new IllegalStateException("이미 해당 항목으로 적립된 학생입니다.");
        }

        // 학생 조회
        Student student = studentRepository.findById(stdNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보를 찾을 수 없습니다."));

        // 항목 조회
        MileageItem mileageItem = mileageItemRepository.findById(dto.getMileageItemId())
                .orElseThrow(() -> new EntityNotFoundException("마일리지 항목을 찾을 수 없습니다."));

        // 수료 검증
        Long eduMngId = mileageItem.getProgram().getEduMngId();
        if (!hasCompletedAllSessions(stdNo, eduMngId)) {
            throw new IllegalStateException("수료 이력이 없는 비교과입니다. 수료한 학생만 적립할 수 있습니다.");
        }

        // 점수 계산: 프로그램 기본 점수 사용
        int calculatedMileage = (dto.getAccMlg() == null)
                ? mileageItem.getProgram().getEduMlg()
                : dto.getAccMlg();

        // 실적 저장
        MileagePerf perf = MileagePerf.builder()
                .student(student)
                .mileageItem(mileageItem)
                .accMlg(calculatedMileage)
                .createdAt(LocalDateTime.now())
                .build();
        mileagePerfRepository.save(perf);

        // 누계 처리 — 존재하면 가산, 없으면 생성 후 가산
        MileageTotal total = mileageTotalRepository.findByStudent_StudentNo(stdNo).orElse(null);
        if (total == null) {
            total = MileageTotal.builder()
                    .studentNo(stdNo)
                    .student(student)
                    .totalScore(0)
                    .build();
            mileageTotalRepository.save(total);
        }
        total.add(calculatedMileage); // 누계 증가

        return MileagePerfResponseDto.from(perf);
    }


    public List<EligibleMileageItemDto> getEligibleItems(String studentNo) {
        final String stdNo = studentNo.trim();

        // 전체 항목 조회 (필요시 조건 검색으로 대체 가능)
        List<MileageItem> allItems = mileageItemRepository.findAll();

        // 이미 적립한 항목 id 세트
        Set<Long> grantedIds = mileagePerfRepository.findAllByStudent_StudentNo(stdNo).stream()
                .map(MileagePerf::getMileageItem)   // perf -> mileageItem
                .filter(Objects::nonNull)           // null이면 제외
                .map(MileageItem::getId)            // item -> id
                .filter(Objects::nonNull)           // 혹시 모를 null id도 제외
                .collect(Collectors.toSet());

        // “수료한 프로그램”에 연결된 항목만 걸러서 반환
        return allItems.stream()
                .filter(mi -> mi.getProgram() != null)
                .filter(mi -> hasCompletedAllSessions(stdNo, mi.getProgram().getEduMngId()))
                .map(mi -> EligibleMileageItemDto.builder()
                        .id(mi.getId())
                        .itemCode(mi.getItemCode())
                        .eduNm(mi.getProgram().getEduNm())
                        .eduMlg(mi.getProgram().getEduMlg())
                        .granted(grantedIds.contains(mi.getId()))
                        .build()
                )
                .toList();
    }

    /* ------------------------------
     * 상세 조회
     * ------------------------------ */
    public MileagePerfResponseDto findById(Long id) {
        MileagePerf perf = mileagePerfRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 실적이 존재하지 않습니다."));
        return MileagePerfResponseDto.from(perf);
    }

    /* ------------------------------
     * 선택 삭제 (누계 차감 포함)
     * ------------------------------ */
    @Transactional
    public void deleteAll(List<Long> ids) {
        for (Long id : ids) {
            MileagePerf perf = mileagePerfRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 실적이 존재하지 않습니다."));

            final String stdNo = perf.getStudent().getStudentNo().trim();
            final int score = perf.getAccMlg();

            // 누계 조회 (없으면 차감 스킵)
            MileageTotal total = mileageTotalRepository.findByStudent_StudentNo(stdNo).orElse(null);
            if (total != null) {
                total.subtract(score); // 더티 체킹으로 UPDATE
            }

            mileagePerfRepository.delete(perf);
        }
    }
}
