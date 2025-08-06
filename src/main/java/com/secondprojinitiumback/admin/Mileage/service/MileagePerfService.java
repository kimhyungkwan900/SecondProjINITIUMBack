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
    public MileagePerfResponseDto register(MileagePerfRequestDto dto) {

        // 학번으로 학생 객체 가져오기
        Student student = studentRepository.findById(dto.getStudentNo())
                .orElseThrow(() -> new EntityNotFoundException("학생 정보를 찾을 수 없습니다."));

        // 마일리지 항목 가져오기
        MileageItem mileageItem = mileageItemRepository.findById(dto.getMileageItemId())
                .orElseThrow(() -> new EntityNotFoundException("마일리지 항목을 찾을 수 없습니다."));

        // 배점 정책 가져오기
        ScorePolicy scorePolicy = scorePolicyRepository.findById(dto.getScorePolicyId())
                .orElseThrow(() -> new EntityNotFoundException("배점 정책을 찾을 수 없습니다."));

        // 배점 정책을 바탕으로 적립 마일리지 계산
        int baseMileage = mileageItem.getProgram().getEduMlg(); //기본 마일리지
        int calculatedMileage = (int) (baseMileage * scorePolicy.getScoreRate()); //배점 비율 적용

        // 실적 등록
        MileagePerf perf = MileagePerf.builder()
                .student(student)
                .mileageItem(mileageItem)
                .scorePolicy(scorePolicy)
                .accMlg(calculatedMileage)
                .createdAt(LocalDateTime.now())
                .build();

        // 실적 저장
        mileagePerfRepository.save(perf);

        // 마일리지 누계 업데이트
        MileageTotal total = mileageTotalRepository.findById(student.getStudentNo())
                .orElseGet(() -> MileageTotal.builder()
                        .student(student)
                        .totalScore(0.0) // 없으면 0점으로 시작
                        .build()
                );

        // 기존 점수에 계산된 마일리지 추가
        total.add(calculatedMileage);

        // 누계 저장
        mileageTotalRepository.save(total);

        // 응답 DTO로 변환하여 반환
        return MileagePerfResponseDto.from(perf);
    }

    // 3. 실적 상세 조회
    public MileagePerfResponseDto findById(Long id) {
        MileagePerf perf = mileagePerfRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 실적이 존재하지 않습니다."));
        return MileagePerfResponseDto.from(perf);
    }

    // 4. 실적 삭제
    public void deleteAll(List<Long> ids) {
        for (Long id : ids) {
            // 실적 조회
            MileagePerf perf = mileagePerfRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("해당 실적이 존재하지 않습니다."));

            Student student = perf.getStudent();         // 실적 주인 : 학생
            int score = perf.getAccMlg();                // 적립된 마일리지

            // 누계 점수 가져오기 (없으면 새로 생성, but 실제로는 존재해야 함)
            MileageTotal total = mileageTotalRepository.findById(student.getStudentNo())
                    .orElseThrow(() -> new EntityNotFoundException("누계 정보가 없습니다."));

            // 점수 차감
            total.subtract(score);
            mileageTotalRepository.save(total);

            // 실적 삭제
            mileagePerfRepository.deleteById(id);
        }
    }
}
