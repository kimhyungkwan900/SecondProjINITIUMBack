package com.secondprojinitiumback.user.consult.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.dto.DscsnInfoSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class DscsnInfoRepositoryCustomImpl implements  DscsnInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    @Override
    public Page<DscsnInfo> getDscsnInfoPageByCondition(DscsnInfoSearchDto dscsnInfoSearchDto, Pageable pageable) {
        return null;
    }
}
