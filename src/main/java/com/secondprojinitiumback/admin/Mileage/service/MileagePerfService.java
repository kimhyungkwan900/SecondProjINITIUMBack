package com.secondprojinitiumback.admin.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileageItem;
import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.domain.ScorePolicy;
import com.secondprojinitiumback.admin.Mileage.dto.*;
import com.secondprojinitiumback.admin.Mileage.repository.MileageItemRepository;
import com.secondprojinitiumback.admin.Mileage.repository.MileagePerfRepository;
import com.secondprojinitiumback.admin.Mileage.repository.MileageTotalRepository;
import com.secondprojinitiumback.admin.Mileage.repository.ScorePolicyRepository;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MileagePerfService {

    private final MileagePerfRepository mileagePerfRepository;
    private final StudentRepository studentRepository;
    private final MileageItemRepository mileageItemRepository;
    private final ScorePolicyRepository scorePolicyRepository;
    private final MileageTotalRepository mileageTotalRepository;

    // 1. 실적 목록 조회 (검색 + 페이징)
    public PageResponseDto<MileagePerfResponseDto> getList(
            PageRequestDto requestDto, String studentNo, String studentName, String subjectName) {

        Pageable pageable = requestDto.toPageable();

        Page<MileagePerf> page = mileagePerfRepository.searchWithPaging(studentNo, studentName, subjectName, pageable);

        List<MileagePerfResponseDto> dtoList = page.getContent().stream()
                .map(MileagePerfResponseDto::from)
                .collect(Collectors.toList());

        return PageResponseDto.<MileagePerfResponseDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(requestDto)
                .totalCount(page.getTotalElements())
                .build();
    }

    // 2. 실적 등록
    @Transactional
    public MileagePerfResponseDto register(MileagePerfRequestDto dto) {

        //학번은 반드시 trim (CHAR(10) 패딩/공백 이슈 방지)
        final String stdNo = dto.getStudentNo().trim();

        // 중복 방지
        if (mileagePerfRepository.existsByStudent_StudentNoAndMileageItem_Id(stdNo, dto.getMileageItemId())) {
            throw new IllegalStateException("이미 해당 항목으로 적립된 학생입니다.");
        }

        // 1) 학생/항목 조회
        Student student = studentRepository.findById(stdNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보를 찾을 수 없습니다."));

        MileageItem mileageItem = mileageItemRepository.findById(dto.getMileageItemId())
                .orElseThrow(() -> new EntityNotFoundException("마일리지 항목을 찾을 수 없습니다."));

        // 2) 점수 계산
        int calculatedMileage;
        ScorePolicy scorePolicy = null;

        if (dto.getScorePolicyId() != null) {
            scorePolicy = scorePolicyRepository.findById(dto.getScorePolicyId())
                    .orElseThrow(() -> new EntityNotFoundException("배점 정책을 찾을 수 없습니다."));
            int base = mileageItem.getProgram().getEduMlg();
            calculatedMileage = (int) (base * scorePolicy.getScoreRate());
        } else {
            calculatedMileage = (dto.getAccMlg() == null)
                    ? mileageItem.getProgram().getEduMlg()
                    : dto.getAccMlg();
        }

        // 3) 실적 저장
        MileagePerf perf = MileagePerf.builder()
                .student(student)
                .mileageItem(mileageItem)
                .scorePolicy(scorePolicy) // null 허용
                .accMlg(calculatedMileage)
                .createdAt(LocalDateTime.now())
                .build();
        mileagePerfRepository.save(perf);

        // 4) 누계 처리 — 존재하면 사용, 없으면 생성(INSERT) 후 가산
        MileageTotal total = mileageTotalRepository.findByStudent_StudentNo(stdNo).orElse(null);

        if (total == null) {
            // Persistable<String> 구현 + @MapsId 사용 전제
            total = MileageTotal.builder()
                    .studentNo(stdNo)     // PK
                    .student(student)     // @MapsId 로 PK 동기화
                    .totalScore(0)
                    .build();             // isNew=true → save() == INSERT
            mileageTotalRepository.save(total);
        }

        // 더티체킹으로 UPDATE
        total.add(calculatedMileage);

        return MileagePerfResponseDto.from(perf);
    }

    // 3. 실적 상세 조회
    public MileagePerfResponseDto findById(Long id) {
        MileagePerf perf = mileagePerfRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 실적이 존재하지 않습니다."));
        return MileagePerfResponseDto.from(perf);
    }

    // 4. 실적 삭제
    @Transactional
    public void deleteAll(List<Long> ids) {
        for (Long id : ids) {
            MileagePerf perf = mileagePerfRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 실적이 존재하지 않습니다."));

            final String stdNo = perf.getStudent().getStudentNo().trim();
            final int score = perf.getAccMlg();

            // ✅ 관계경로 조회(CHAR(10) 패딩 문제 회피) + 없으면 차감 스킵
            MileageTotal total = mileageTotalRepository.findByStudent_StudentNo(stdNo).orElse(null);
            if (total != null) {
                total.subtract(score); // 더티체킹으로 UPDATE
            }

            mileagePerfRepository.delete(perf);
        }
    }
}
