package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.repository.BehaviorIndicatorRepository;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.user.coreCompetency.dto.BehaviorIndicatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 행동지표(BehaviorIndicator) 관련 비즈니스 로직 처리 서비스 클래스
 */
@Service
@RequiredArgsConstructor
public class BehaviorIndicatorService {

    private final BehaviorIndicatorRepository behaviorIndicatorRepository;
    private final SchoolSubjectRepository schoolSubjectRepository;

    /**
     * 공통 여부 및 전공 코드에 따라 행동지표 목록을 조회하는 메서드
     *
     * @param subjectCode 전공 코드 (ex. "ENG", "BUS") – 공통 문항이면 null 가능
     * @param isCommon    공통 문항 여부 (true: 공통 문항, false: 전공 문항)
     * @return 행동지표 DTO 리스트
     */
    public List<BehaviorIndicatorDto> getIndicatorsBySubject(String subjectCode, boolean isCommon) {
        if (isCommon) {
            // 공통 문항인 경우 공통 코드 "Y"에 해당하는 행동지표만 조회
            return behaviorIndicatorRepository.findByIsCommon("Y")
                    .stream().map(BehaviorIndicatorDto::fromEntity).collect(Collectors.toList());
        } else {
            // 전공 문항인 경우 전공 코드로 SchoolSubject 엔티티 조회 후, 해당 전공 + 공통 여부 "N" 조건으로 조회
            SchoolSubject subject = schoolSubjectRepository.findByCode(subjectCode)
                    .orElseThrow(() -> new IllegalArgumentException("해당 학과 코드가 존재하지 않습니다."));
            return behaviorIndicatorRepository.findByIsCommonAndSchoolSubject("N", subject)
                    .stream().map(BehaviorIndicatorDto::fromEntity).collect(Collectors.toList());
        }
    }
}
