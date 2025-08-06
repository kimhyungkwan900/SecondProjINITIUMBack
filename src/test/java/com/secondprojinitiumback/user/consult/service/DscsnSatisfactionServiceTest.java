package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.domain.DscsnSatisfaction;
import com.secondprojinitiumback.user.consult.dto.common.DscsnSatisfactionDto;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import com.secondprojinitiumback.user.consult.repository.DscsnSatisfactionRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DscsnSatisfactionServiceTest {

    @Mock
    private DscsnInfoRepository dscsnInfoRepository;

    @Mock
    private DscsnSatisfactionRepository dscsnSatisfactionRepository;

    @InjectMocks
    private DscsnSatisfactionService dscsnSatisfactionService;

    private DscsnInfo sampleInfo;
    private DscsnSatisfactionDto dscsnSatisfactionDto;

    @BeforeEach
    void setup() {
        // 공통 샘플 DscsnInfo
        sampleInfo = DscsnInfo.builder()
                .dscsnInfoId("AI1001")
                .build();

        // 입력 DTO
        dscsnSatisfactionDto = DscsnSatisfactionDto.builder()
                .dscsnSatisfyScore("만족")
                .dscsnImp("유익한 상담이었습니다.")
                .dscsnInfoId(sampleInfo.getDscsnInfoId())
                .build();
    }

    //--- saveDscsnSatisfaction: 정상 처리 (첫 시퀀스)
    @Test
    void saveDscsnSatisfaction_success_firstSequence() {
        // given: satisfactionRepository에서 마지막이 없다고 응답
        when(dscsnInfoRepository.findById("AI1001"))
                .thenReturn(Optional.of(sampleInfo));
        when(dscsnSatisfactionRepository
                .findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc("AS"))
                .thenReturn(null);

        // when
        dscsnSatisfactionService.saveDscsnSatisfaction(dscsnSatisfactionDto);

        // then: save()된 엔티티 검증
        ArgumentCaptor<DscsnSatisfaction> captor =
                ArgumentCaptor.forClass(DscsnSatisfaction.class);
        verify(dscsnSatisfactionRepository).save(captor.capture());

        DscsnSatisfaction saved = captor.getValue();

        assertThat(saved.getDscsnSatisfyId()).isEqualTo("AS0001");
        assertThat(saved.getDscsnSatisfyScore()).isEqualTo("만족");
        assertThat(saved.getDscsnImp()).isEqualTo("유익한 상담이었습니다.");
        assertThat(saved.getDscsnInfo()).isSameAs(sampleInfo);
    }

    //--- saveDscsnSatisfaction: DscsnInfo가 없으면 예외
    @Test
    void saveDscsnSatisfaction_noInfo_throws() {
        when(dscsnInfoRepository.findById("A1002")).thenReturn(Optional.empty());

        dscsnSatisfactionDto = DscsnSatisfactionDto.builder()
                .dscsnInfoId("A1002")
                .build();

        assertThatThrownBy(() -> dscsnSatisfactionService.saveDscsnSatisfaction(dscsnSatisfactionDto))
                .isInstanceOf(EntityExistsException.class);
        verify(dscsnInfoRepository).findById("A1002");
        verifyNoMoreInteractions(dscsnSatisfactionRepository);
    }

    //--- getNextSatisfactionSequence: 첫 호출 (null → "0001")
    @Test
    void getNextSatisfactionSequence_first() {
        when(dscsnSatisfactionRepository
                .findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc("AS"))
                .thenReturn(null);

        String seq = dscsnSatisfactionService.getNextSatisfactionSequence("AS");
        assertThat(seq).isEqualTo("0001");
    }

    //--- getNextSatisfactionSequence: 기존 마지막이 "AS0010" → "0011"
    @Test
    void getNextSatisfactionSequence_increment() {
        DscsnSatisfaction last = DscsnSatisfaction.builder()
                .dscsnSatisfyId("AS0010")
                .build();

        when(dscsnSatisfactionRepository
                .findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc("AS"))
                .thenReturn(last);

        String seq = dscsnSatisfactionService.getNextSatisfactionSequence("AS");
        assertThat(seq).isEqualTo("0011");
    }
}
