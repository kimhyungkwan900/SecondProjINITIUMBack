package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnApply;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import com.secondprojinitiumback.user.consult.dto.common.DscsnResultDto;
import com.secondprojinitiumback.user.consult.dto.responsedto.DscsnInfoResponseDto;
import com.secondprojinitiumback.user.consult.repository.DscsnInfoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DscsnInfoServiceTest {

    @Mock
    private DscsnInfoRepository dscsnInfoRepository;

    @InjectMocks
    private DscsnInfoService dscsnInfoService;

    private DscsnApply sampleApply;

    @BeforeEach
    void setup() {
        sampleApply = DscsnApply.builder()
                .dscsnApplyId("A0001")
                .build();
    }

    //--- createDscsnInfo: 첫 시퀀스 ("0001") 생성 검증
    @Test
    void createDscsnInfo_firstSequence() {

        when(dscsnInfoRepository.findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc("AI"))
                .thenReturn(null);

        dscsnInfoService.createDscsnInfo(sampleApply);

        ArgumentCaptor<DscsnInfo> captor = ArgumentCaptor.forClass(DscsnInfo.class);
        verify(dscsnInfoRepository).save(captor.capture());
        DscsnInfo saved = captor.getValue();

        // 접두사 'A' + "I" -> "AI0001"
        assertThat(saved.getDscsnInfoId()).isEqualTo("AI0001");
        assertThat(saved.getDscsnStatus()).isEqualTo("예약대기");
        assertThat(saved.getDscsnReleaseYn()).isEqualTo("N");
        assertThat(saved.getDscsnResultCn()).isNull();
        assertThat(saved.getDscsnApply()).isSameAs(sampleApply);
    }

    //--- createDscsnInfo: 기존 마지막이 있을 때 시퀀스 증가 검증
    @Test
    void createDscsnInfo_incrementSequence() {
        // 기존 마지막 ID가 "AI0020"이라고 가정
        DscsnInfo last = DscsnInfo.builder()
                .dscsnInfoId("AI0020")
                .build();
        when(dscsnInfoRepository.findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc("AI"))
                .thenReturn(last);

        // applyId가 'A'로 시작하면 prefix는 "AI"
        sampleApply.builder()
                .dscsnApplyId("A2000")
                .build();
        dscsnInfoService.createDscsnInfo(sampleApply);

        ArgumentCaptor<DscsnInfo> captor = ArgumentCaptor.forClass(DscsnInfo.class);
        verify(dscsnInfoRepository).save(captor.capture());
        assertThat(captor.getValue().getDscsnInfoId()).isEqualTo("AI0021");
    }

    //--- getDscsnInfo: DTO 매핑 검증 (applyDto가 null인 경우)
    @Test
    void getDscsnInfo_minimalMapping() {
        DscsnInfoSearchDto searchDto = new DscsnInfoSearchDto();
        Pageable pageReq = PageRequest.of(0, 10);

        // dscsnApply를 sampleApply 으로 만든 샘플 DscsnInfo
        DscsnInfo dscsnInfo = DscsnInfo.builder()
                .dscsnInfoId("AI1234")
                .dscsnStatus("예약대기")
                .dscsnResultCn(null)
                .dscsnReleaseYn("N")
                .dscsnApply(null)
                .build();

        Page<DscsnInfo> domainPage = new PageImpl<>(List.of(dscsnInfo), pageReq, 1);
        when(dscsnInfoRepository.getDscsnInfoPageByCondition(searchDto, pageReq))
                .thenReturn(domainPage);

        Page<DscsnInfoResponseDto> result = dscsnInfoService.getDscsnInfo(searchDto, pageReq);

        assertThat(result.getTotalElements()).isEqualTo(1);
        DscsnInfoResponseDto dto = result.getContent().getFirst();
        assertThat(dto.getDscsnInfoId()).isEqualTo("AI1234");
        assertThat(dto.getDscsnStatus()).isEqualTo("예약대기");
        assertThat(dto.getDscsnResultCn()).isNull();
        assertThat(dto.getDscsnReleaseYn()).isEqualTo("N");
        assertThat(dto.getDscsnApplyDto()).isNull();
    }

    //--- updateDscsnStatus: 정상 변경 검증
    @Test
    void updateDscsnStatus_success() {
        DscsnInfo dscsnInfo = DscsnInfo.builder()
                .dscsnInfoId("AI1001")
                .dscsnStatus("예약대기")
                .build();

        when(dscsnInfoRepository.findById("AI1001")).thenReturn(Optional.of(dscsnInfo));

        dscsnInfoService.updateDscsnStatus("AI1001", "예약완료");

        assertThat(dscsnInfo.getDscsnStatus()).isEqualTo("예약완료");
    }

    //--- updateDscsnStatus: 미발견 시 예외
    @Test
    void updateDscsnStatus_missing_throws() {
        when(dscsnInfoRepository.findById("AI1002")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> dscsnInfoService.updateDscsnStatus("AI1002", "상담취소"))
                .isInstanceOf(EntityExistsException.class);
    }

    //--- registerDscsnResult: 정상 등록 검증
    @Test
    void registerDscsnResult_success() {
        DscsnInfo dscsnInfo = DscsnInfo.builder()
                .dscsnInfoId("AI2002")
                .dscsnStatus("예약대기")
                .dscsnReleaseYn("N")
                .dscsnResultCn(null)
                .build();

        when(dscsnInfoRepository.findById("AI2002")).thenReturn(Optional.of(dscsnInfo));

        DscsnResultDto dto = DscsnResultDto.builder()
                .dscsnInfoId("AI2002")
                .releaseYn("Y")
                .result("원할한 상담이었습니다.")
                .build();

        dscsnInfoService.registerDscsnResult(dto);

        assertThat(dscsnInfo.getDscsnReleaseYn()).isEqualTo("Y");
        assertThat(dscsnInfo.getDscsnResultCn()).isEqualTo("원할한 상담이었습니다.");
        assertThat(dscsnInfo.getDscsnStatus()).isEqualTo("상담완료");
    }

    //--- registerDscsnResult: 미발견 시 예외
    @Test
    void registerDscsnResult_missing_throws() {
        when(dscsnInfoRepository.findById("AI2003")).thenReturn(Optional.empty());
        DscsnResultDto dto = DscsnResultDto.builder()
                .dscsnInfoId("AI2003")
                .build();

        assertThatThrownBy(() -> dscsnInfoService.registerDscsnResult(dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    //--- getNextInfoSequence: 첫 호출 ("0001")
    @Test
    void getNextInfoSequence_first() {
        when(dscsnInfoRepository.findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc("AI"))
                .thenReturn(null);

        String seq = dscsnInfoService.getNextInfoSequence("AI");
        assertThat(seq).isEqualTo("0001");
    }

    //--- getNextInfoSequence: 증가 호출
    @Test
    void getNextInfoSequence_increment() {
        DscsnInfo last = DscsnInfo.builder()
                .dscsnInfoId("AI0059")
                .build();

        when(dscsnInfoRepository.findTopByDscsnInfoIdStartingWithOrderByDscsnInfoIdDesc("AI"))
                .thenReturn(last);

        String seq = dscsnInfoService.getNextInfoSequence("AI");
        assertThat(seq).isEqualTo("0060");
    }
}