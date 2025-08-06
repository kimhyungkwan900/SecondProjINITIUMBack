// src/test/java/com/secondprojinitiumback/admin/coreCompetency/service/BehaviorIndicatorServiceTest.java
package com.secondprojinitiumback.admin.coreCompetency.service;

import com.secondprojinitiumback.admin.coreCompetency.repository.BehaviorIndicatorRepository;
import com.secondprojinitiumback.common.domain.SchoolSubject;
import com.secondprojinitiumback.common.repository.SchoolSubjectRepository;
import com.secondprojinitiumback.admin.coreCompetency.dto.BehaviorIndicatorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * BehaviorIndicatorService의 행동지표 조회 기능 단위 테스트 클래스
 */

class BehaviorIndicatorServiceTest {

    // 행동지표 및 학과 Repository를 Mock 객체로 선언
    @Mock
    private BehaviorIndicatorRepository behaviorIndicatorRepository;
    @Mock
    private SchoolSubjectRepository schoolSubjectRepository;

    // Service에 Mock 객체 주입
    @InjectMocks
    private BehaviorIndicatorService service;

    /**
     * 각 테스트 실행 전 Mockito의 Mock 객체 초기화
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * [공통 문항] 행동지표 조회 성공 케이스
     * - 공통 코드 "Y"에 해당하는 행동지표만 반환되는지 검증
     */
    @Test
    void getIndicatorsBySubject_common_success() {
        // 공통 행동지표 DTO 리스트 Mock 반환 설정
        BehaviorIndicatorDto dto = mock(BehaviorIndicatorDto.class);
        when(behaviorIndicatorRepository.findByIsCommon("Y"))
                .thenReturn(Collections.singletonList(mock(com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator.class)));

        // fromEntity 메서드 Mock (static 메서드라면 PowerMockito 등 필요)
        try (MockedStatic<BehaviorIndicatorDto> mockedStatic = mockStatic(BehaviorIndicatorDto.class)) {
            when(BehaviorIndicatorDto.fromEntity(any())).thenReturn(dto);

            List<BehaviorIndicatorDto> result = service.getIndicatorsBySubject(null, true);

            assertThat(result).hasSize(1);
            verify(behaviorIndicatorRepository).findByIsCommon("Y");
        }

    }

    /**
     * [전공 문항] 행동지표 조회 성공 케이스
     * - 전공 코드와 공통 여부 "N"에 해당하는 행동지표만 반환되는지 검증
     */
    @Test
    void getIndicatorsBySubject_major_success() {
        // 전공 학과 엔티티 및 행동지표 DTO 리스트 Mock 반환 설정
        SchoolSubject subject = mock(SchoolSubject.class);
        when(schoolSubjectRepository.findBySubjectCode("ENG")).thenReturn(Optional.of(subject));
        when(behaviorIndicatorRepository.findByIsCommonAndSchoolSubject("N", subject))
                .thenReturn(Collections.singletonList(mock(com.secondprojinitiumback.admin.coreCompetency.domain.BehaviorIndicator.class)));
        mockStatic(BehaviorIndicatorDto.class);
        BehaviorIndicatorDto dto = mock(BehaviorIndicatorDto.class);
        when(BehaviorIndicatorDto.fromEntity(any())).thenReturn(dto);

        // 서비스 호출 및 결과 검증
        List<BehaviorIndicatorDto> result = service.getIndicatorsBySubject("ENG", false);

        assertThat(result).hasSize(1);
        // 전공 코드로 학과 조회 및 행동지표 조회 메서드가 호출되었는지 검증
        verify(schoolSubjectRepository).findBySubjectCode("ENG");
        verify(behaviorIndicatorRepository).findByIsCommonAndSchoolSubject("N", subject);
    }

    /**
     * [전공 문항] 학과 코드가 존재하지 않을 때 예외 발생 케이스
     */
    @Test
    void getIndicatorsBySubject_major_subjectNotFound() {
        // 학과 조회 시 빈 값 반환 Mock 설정
        when(schoolSubjectRepository.findBySubjectName("BUS")).thenReturn(Optional.empty());

        // 예외 발생 검증 (존재하지 않는 학과 코드)
        assertThrows(IllegalArgumentException.class, () -> service.getIndicatorsBySubject("BUS", false));
    }
}