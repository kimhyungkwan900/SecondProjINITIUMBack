package com.secondprojinitiumback.user.consult.service;

import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import com.secondprojinitiumback.user.consult.repository.DscsnKindRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DscsnKindServiceTest {

    @Mock
    private DscsnKindRepository dscsnKindRepository;

    @InjectMocks
    private DscsnKindService dscsnKindService;

    private DscsnKindDto dscsnKindDto;

    private DscsnKind dscsnKind;

    @BeforeEach
    void setUp() {
        dscsnKindDto = DscsnKindDto.builder()
                .dscsnKindId(null)
                .dscsnKindName(null)
                .dscsnTypeName("진로취업상담")
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
                        .dscsnTypeName("진로취업상담").build(),
                DscsnKind.builder()
                        .dscsnKindId("A001")
                        .dscsnKindName("대학원 진학")
                        .dscsnTypeName("지도교수상담").build()
        );
        Page<DscsnKind> domainPage = new PageImpl<>(list, pageReq, list.size());

        when(dscsnKindRepository.getDscsnKindPageByCondition(dscsnKindDto, pageReq))
                .thenReturn(domainPage);

        Page<DscsnKindDto> result = dscsnKindService.getDscsnKind(dscsnKindDto, pageReq);

        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent())
                .extracting(dscsnKindDto.getDscsnKindId(),
                        dscsnKindDto.getDscsnKindName(),
                        dscsnKindDto.getDscsnTypeName())
                .containsExactly(
                        tuple("C001", "진로방향 고민", "진로취업상담"),
                        tuple("C002", "취업시장 탐색", "진로취업상담")
                );
    }

//    //--- saveDscsnKind: 저장 인자 검증
//    @Test
//    void saveDscsnKind_callsSaveWithEntity() {
//        service.saveDscsnKind(inputDto);
//
//        ArgumentCaptor<DscsnKind> captor = ArgumentCaptor.forClass(DscsnKind.class);
//        verify(kindRepository).save(captor.capture());
//
//        DscsnKind saved = captor.getValue();
//        assertThat(saved.getDscsnKindId()).isEqualTo("K100");
//        assertThat(saved.getDscsnKindName()).isEqualTo("심리상담");
//        assertThat(saved.getDscsnTypeName()).isEqualTo("P");
//    }
//
//    //--- updateDscsnKind: 성공 경로
//    @Test
//    void updateDscsnKind_success() {
//        when(kindRepository.findById("K100"))
//                .thenReturn(Optional.of(domain));
//
//        DscsnKindDto updateDto = DscsnKindDto.builder()
//                .dscsnKindId("K100")
//                .dscsnKindName("변경된이름")
//                .dscsnTypeName("NewType")
//                .build();
//
//        service.updateDscsnKind(updateDto);
//
//        // 도메인 객체 자체가 변경되었는지 검증
//        assertThat(domain.getDscsnKindName()).isEqualTo("변경된이름");
//        assertThat(domain.getDscsnTypeName()).isEqualTo("NewType");
//
//        // save() 호출 없이 Dirty Checking 으로 처리됨
//        verify(kindRepository, never()).save(any());
//    }
//
//    //--- updateDscsnKind: 없는 ID 예외
//    @Test
//    void updateDscsnKind_notFound_throws() {
//        when(kindRepository.findById("K100")).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() ->
//                service.updateDscsnKind(inputDto)
//        ).isInstanceOf(EntityExistsException.class);
//
//        verify(kindRepository).findById("K100");
//    }
//
//    //--- deleteDscsnKind: 삭제 호출 검증
//    @Test
//    void deleteDscsnKind_callsDeleteAllById() {
//        List<String> ids = List.of("K1", "K2", "K3");
//        service.deleteDscsnKind(ids);
//        verify(kindRepository).deleteAllById(ids);
//    }
}