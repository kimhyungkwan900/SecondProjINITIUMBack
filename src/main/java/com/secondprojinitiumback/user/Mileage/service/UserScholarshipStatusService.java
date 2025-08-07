package com.secondprojinitiumback.user.Mileage.service;

import com.secondprojinitiumback.admin.Mileage.domain.ScholarshipApply;
import com.secondprojinitiumback.admin.Mileage.dto.PageRequestDto;
import com.secondprojinitiumback.admin.Mileage.dto.PageResponseDto;
import com.secondprojinitiumback.admin.Mileage.repository.ScholarshipApplyRepository;
import com.secondprojinitiumback.user.Mileage.dto.UserScholarshipStatusDto;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserScholarshipStatusService {

    private final ScholarshipApplyRepository scholarshipApplyRepository;

    public PageResponseDto<UserScholarshipStatusDto> getMyScholarshipStatus(String studentNo, PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.toPageable();

        Page<ScholarshipApply> result = scholarshipApplyRepository.findAllByStudent_StudentNo(studentNo, pageable);

        List<UserScholarshipStatusDto> dtoList = result.getContent().stream()
                .map(UserScholarshipStatusDto::from)
                .collect(Collectors.toList());

        return PageResponseDto.<UserScholarshipStatusDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .totalCount(result.getTotalElements())
                .build();
    }
}
