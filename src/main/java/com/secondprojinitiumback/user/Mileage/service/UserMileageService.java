package com.secondprojinitiumback.user.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.MileagePerf;
import com.secondprojinitiumback.admin.Mileage.domain.MileageTotal;
import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.MileagePerfRepository;
import com.secondprojinitiumback.admin.Mileage.repository.MileageTotalRepository;
import com.secondprojinitiumback.user.Mileage.dto.UserMileageRecordDto;
import com.secondprojinitiumback.user.Mileage.dto.UserMileageTotalDto;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMileageService {

    private final StudentRepository studentRepository;
    private final MileagePerfRepository mileagePerfRepository;
    private final MileageTotalRepository mileageTotalRepository;

    // 누적 점수 확인
    public UserMileageTotalDto getTotal(String studentNo) {
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException("학생 정보가 없습니다."));
        MileageTotal total = mileageTotalRepository.findById(student.getStudentNo())
                .orElseThrow(() -> new EntityNotFoundException("누적 점수가 없습니다."));

        return UserMileageTotalDto.from(total);
    }

    // 지급 내역 목록 조회 (페이징)
//    public PageResponseDto<UserMileageRecordDto> getRecordList(String studentNo, PageRequestDto requestDto) {
//
//        Student student = studentRepository.findById(studentNo)
//                .orElseThrow(() -> new EntityNotFoundException("학생 정보가 없습니다."));
//        Pageable pageable = requestDto.toPageable();
//
//        Page<MileagePerf> page = mileagePerfRepository.findByStudent(student, pageable);
//        double totalScore = mileageTotalRepository.findById(student.getStudentNo())
//                .map(MileageTotal::getTotalScore)
//                .orElse(0.0);
//
//        List<UserMileageRecordDto> dtoList = page.getContent().stream()
//                .map(perf -> UserMileageRecordDto.from(perf, totalScore))
//                .collect(Collectors.toList());
//
//        return PageResponseDto.<UserMileageRecordDto>withAll()
//                .dtoList(dtoList)
//                .pageRequestDto(requestDto)
//                .totalCount(page.getTotalElements())
//                .build();
//    }
}
