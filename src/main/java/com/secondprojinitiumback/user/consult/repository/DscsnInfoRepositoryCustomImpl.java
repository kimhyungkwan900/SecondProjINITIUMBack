package com.secondprojinitiumback.user.consult.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.domain.QDscsnInfo;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class DscsnInfoRepositoryCustomImpl implements  DscsnInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    //상담분야, 상담 시작일, 상담 종료일, 상담상태, 연도, 학기, 상담항목, 학번, 성명, 학적상태, 소속(학과)

    @Override
    public Page<DscsnInfo> getDscsnInfoPageByCondition(DscsnInfoSearchDto dscsnInfoSearchDto, Pageable pageable) {
        QDscsnInfo dscsnInfo = QDscsnInfo.dscsnInfo;    //자동 생성된 Q클래스

        // 동적 where절 조립
        BooleanBuilder builder = new BooleanBuilder();

        //상담분야
        if(StringUtils.hasText(dscsnInfoSearchDto.getDscsnType())){
            builder.and(dscsnInfo.dscsnApply.dscsnDt.dscsnDtId.startsWith(dscsnInfoSearchDto.getDscsnType()));
        }
        //상담시작일

        //상담종료일

        //상담상태

        //연도,학기

        //상담항목

        //학번

        //성명

        //학적상태

        //소속(학과)
        return null;
    }
}
