package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DscsnKindServiceTest {

    @Mock
    private DscsnKindRepository dscsnKindRepository;

    @InjectMocks
    private DscsnKindService dscsnKindService;

    private DscsnKindDto dscsnKindDto;

    private DscsnKindDto searchDto;

    private DscsnKind dscsnKind;

    @BeforeEach
    void setUp() {
        searchDto = DscsnKindDto.builder()
                .dscsnKindId(null)
                .dscsnKindName(null)
                .dscsnTypeName("진로취업상담")
                .build();

        dscsnKindDto = DscsnKindDto.builder()
                .dscsnKindId("A002")
                .dscsnKindName("대학원 진학")
                .dscsnTypeName("지도교수상담")
                .build();

        dscsnKind = DscsnKind.builder()
                .dscsnKindId("C101")
                .dscsnKindName("진로 방향 고민")
                .dscsnTypeName("진로취업상담")
                .build();
    }

    //--- getDscsnKind: 페이지 및 매핑 검증
    @Test
    void getDscsnKind_mapsDomainToDto() {
        Pageable pageReq = PageRequest.of(0, 5);
        List<DscsnKind> list = List.of(
                DscsnKind.builder()
                        .dscsnKindId("C001")
                        .dscsnKindName("진로방향 고민")
                        .dscsnTypeName("진로취업상담").build(),
                DscsnKind.builder()
                        .dscsnKindId("C002")
                        .dscsnKindName("취업시장 탐색")
                        .dscsnTypeName("진로취업상담").build()
        );
        Page<DscsnKind> domainPage = new PageImpl<>(list, pageReq, list.size());

        when(dscsnKindRepository.getDscsnKindPageByCondition(searchDto, pageReq))
                .thenReturn(domainPage);

        Page<DscsnKindDto> result = dscsnKindService.getDscsnKind(searchDto, pageReq);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().getFirst().getDscsnKindId()).isEqualTo("C001");
        assertThat(result.getContent().getFirst().getDscsnKindName()).isEqualTo("진로방향 고민");
        assertThat(result.getContent().getFirst().getDscsnTypeName()).isEqualTo("진로취업상담");

        assertThat(result.getContent().get(1).getDscsnKindId()).isEqualTo("C002");
        assertThat(result.getContent().get(1).getDscsnKindName()).isEqualTo("취업시장 탐색");
        assertThat(result.getContent().get(1).getDscsnTypeName()).isEqualTo("진로취업상담");
    }

    //--- saveDscsnKind: 저장 인자 검증
    @Test
    void saveDscsnKind_callsSaveWithEntity() {
        dscsnKindService.saveDscsnKind(dscsnKindDto);

        ArgumentCaptor<DscsnKind> captor = ArgumentCaptor.forClass(DscsnKind.class);
        verify(dscsnKindRepository).save(captor.capture());

        DscsnKind saved = captor.getValue();
        assertThat(saved.getDscsnKindId()).isEqualTo("A002");
        assertThat(saved.getDscsnKindName()).isEqualTo("대학원 진학");
        assertThat(saved.getDscsnTypeName()).isEqualTo("지도교수상담");
    }

    //--- updateDscsnKind: 성공 경로
    @Test
    void updateDscsnKind_success() {
        when(dscsnKindRepository.findById("A002"))
                .thenReturn(Optional.of(dscsnKind));

        DscsnKindDto updateDto = DscsnKindDto.builder()
                .dscsnKindId("A002")
                .dscsnKindName("비교과 활동")
                .dscsnTypeName("지도교수상담")
                .build();

        dscsnKindService.updateDscsnKind(updateDto);

        // 도메인 객체 자체가 변경되었는지 검증
        assertThat(dscsnKind.getDscsnKindName()).isEqualTo("비교과 활동");
        assertThat(dscsnKind.getDscsnTypeName()).isEqualTo("지도교수상담");
    }

    //--- updateDscsnKind: 없는 ID 예외
    @Test
    void updateDscsnKind_notFound_throws() {
        when(dscsnKindRepository.findById("A002")).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                dscsnKindService.updateDscsnKind(dscsnKindDto)
        ).isInstanceOf(EntityExistsException.class);

        verify(dscsnKindRepository).findById("A002");
    }

    //--- deleteDscsnKind: 삭제 호출 검증
    @Test
    void deleteDscsnKind_callsDeleteAllById() {
        List<String> ids = List.of("L001", "L002", "L003");
        dscsnKindService.deleteDscsnKind(ids);
        verify(dscsnKindRepository).deleteAllById(ids);
    }
}