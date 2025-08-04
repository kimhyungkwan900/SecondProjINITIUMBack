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

//    @BeforeEach
//    void setup() {
//        // 공통 샘플 DscsnInfo
//        sampleInfo = DscsnInfo.builder()
//                .dscsnInfoId("AI1001")
//                .build();
//        // prefix 'A' → prefix+"S" = "AS"
//        // 입력 DTO
//        dto = new DscsnSatisfactionDto();
//        dto.setDscsnInfoId("A1001");
//        dto.setDscsnSatisfyScore(4);
//        dto.setDscsnImp("매우만족");
//    }
//
//    //--- saveDscsnSatisfaction: 정상 처리 (첫 시퀀스)
//    @Test
//    void saveDscsnSatisfaction_success_firstSequence() {
//        // given: satisfactionRepository에서 마지막이 없다고 응답
//        when(infoRepository.findById("A1001"))
//                .thenReturn(Optional.of(sampleInfo));
//        when(satisfactionRepository
//                .findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc("AS"))
//                .thenReturn(null);
//
//        // when
//        service.saveDscsnSatisfaction(dto);
//
//        // then: save()된 엔티티 검증
//        ArgumentCaptor<DscsnSatisfaction> captor =
//                ArgumentCaptor.forClass(DscsnSatisfaction.class);
//        verify(satisfactionRepository).save(captor.capture());
//
//        DscsnSatisfaction saved = captor.getValue();
//        // ID는 DTO의 dscsnInfoId 그대로 사용
//        assertThat(saved.getDscsnSatisfyId()).isEqualTo("A1001");
//        assertThat(saved.getDscsnSatisfyScore()).isEqualTo(4);
//        assertThat(saved.getDscsnImp()).isEqualTo("매우만족");
//        assertThat(saved.getDscsnInfo()).isSameAs(sampleInfo);
//    }
//
//    //--- saveDscsnSatisfaction: DscsnInfo가 없으면 예외
//    @Test
//    void saveDscsnSatisfaction_noInfo_throws() {
//        when(infoRepository.findById("X")).thenReturn(Optional.empty());
//        dto.setDscsnInfoId("X");
//
//        assertThatThrownBy(() -> service.saveDscsnSatisfaction(dto))
//                .isInstanceOf(EntityExistsException.class);
//        verify(infoRepository).findById("X");
//        verifyNoMoreInteractions(satisfactionRepository);
//    }
//
//    //--- getNextSatisfactionSequence: 첫 호출 (null → "0001")
//    @Test
//    void getNextSatisfactionSequence_first() {
//        when(satisfactionRepository
//                .findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc("BS"))
//                .thenReturn(null);
//
//        String seq = service.getNextSatisfactionSequence("BS");
//        assertThat(seq).isEqualTo("0001");
//    }
//
//    //--- getNextSatisfactionSequence: 기존 마지막이 "CS0010" → "0011"
//    @Test
//    void getNextSatisfactionSequence_increment() {
//        DscsnSatisfaction last = new DscsnSatisfaction();
//        last.setDscsnSatisfyId("CS0010");
//        when(satisfactionRepository
//                .findTopByDscsnSatisfyIdStartingWithOrderByDscsnSatisfyIdDesc("CS"))
//                .thenReturn(last);
//
//        String seq = service.getNextSatisfactionSequence("CS");
//        assertThat(seq).isEqualTo("0011");
//    }
}
