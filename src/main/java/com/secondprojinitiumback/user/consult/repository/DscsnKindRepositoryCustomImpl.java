package com.secondprojinitiumback.user.consult.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.domain.QDscsnKind;
import com.secondprojinitiumback.user.consult.dto.common.DscsnKindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class DscsnKindRepositoryCustomImpl implements DscsnKindRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<DscsnKind> getDscsnKindPageByCondition(DscsnKindDto dscsnKindDto, Pageable pageable) {
        QDscsnKind dscsnKind = QDscsnKind.dscsnKind;

        // 동적 where 조립
        BooleanBuilder builder = new BooleanBuilder();

        //상담유형 코드
        if(StringUtils.hasText(dscsnKindDto.getDscsnKindId())) {
            builder.and(dscsnKind.dscsnKindId.eq(dscsnKindDto.getDscsnKindId()));
        }
        //상담항목명 이름
        if(StringUtils.hasText(dscsnKindDto.getDscsnKindName())) {
            builder.and(dscsnKind.dscsnKindName.eq(dscsnKindDto.getDscsnKindName()));
        }
        //상담 유형
        if(StringUtils.hasText(dscsnKindDto.getDscsnTypeName())) {
            builder.and(dscsnKind.dscsnTypeName.eq(dscsnKindDto.getDscsnTypeName()));
        }

        List<DscsnKind> dscsnKindList = queryFactory
                .selectFrom(dscsnKind)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(dscsnKind.count())
                .from(dscsnKind)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(dscsnKindList, pageable, total);
    }
}
