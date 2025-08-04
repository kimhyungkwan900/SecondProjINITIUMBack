//package com.secondprojinitiumback.user.consult.repository;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
//import com.secondprojinitiumback.user.consult.domain.QDscsnInfo;
//import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class DscsnInfoRepositoryCustomImplTest {
//
//    @Mock
//    private JPAQueryFactory queryFactory;
//
//    @InjectMocks
//    private DscsnInfoRepositoryCustomImpl repo;
//
//    // Mocking QueryDSL Q클래스와 쿼리 객체들
//    @Spy
//    private QDscsnInfo dscsnInfo = QDscsnInfo.dscsnInfo;
//
//    @Mock
//    private JPAQuery<DscsnInfo> selectQuery;
//
//    @Mock
//    private JPAQuery<Long> countQuery;
//
//    @BeforeEach
//    void setUp() {
//        // selectFrom → selectQuery 반환
//        when(queryFactory.selectFrom(dscsnInfo))
//                .thenReturn(selectQuery);
//        // count 쿼리
//        when(queryFactory
//                .select(dscsnInfo.count()))
//                .thenReturn(countQuery);
//        // 공통 where 절 체이닝
//        when(selectQuery.where(any()))
//                .thenReturn(selectQuery);
//        when(countQuery.from(dscsnInfo))
//                .thenReturn(countQuery);
//        when(countQuery.where(any()))
//                .thenReturn(countQuery);
//        // 페이징
//        when(selectQuery.offset(anyLong()))
//                .thenReturn(selectQuery);
//        when(selectQuery.limit(anyLong()))
//                .thenReturn(selectQuery);
//    }
//
//    @Test
//    void getDscsnInfoPageByCondition_returnsCorrectPage() {
//        // given
//        DscsnInfoSearchDto dto = new DscsnInfoSearchDto();
//        dto.setUserType("S");
//        dto.setSerialNo("2021001");
//        dto.setStartDate("20250101");
//        dto.setEndDate("20251231");
//        Pageable pageReq = Pageable.ofSize(10).withPage(0);
//
//        DscsnInfo sample = new DscsnInfo();
//        // ... sample 필드 설정 (필요하면)
//        List<DscsnInfo> expectedList = List.of(sample);
//
//        when(selectQuery.fetch())
//                .thenReturn(expectedList);
//        when(countQuery.fetchOne())
//                .thenReturn(1L);
//
//        // when
//        Page<DscsnInfo> page = repo.getDscsnInfoPageByCondition(dto, pageReq);
//
//        // then
//        assertThat(page.getContent()).isEqualTo(expectedList);
//        assertThat(page.getTotalElements()).isEqualTo(1L);
//    }
//}
