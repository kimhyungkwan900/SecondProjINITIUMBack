package com.secondprojinitiumback.user.consult.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.user.consult.domain.DscsnKind;
import com.secondprojinitiumback.user.consult.domain.DscsnSchedule;
import com.secondprojinitiumback.user.consult.domain.QDscsnSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class DscsnScheduleRepositoryCustomImpl implements DscsnScheduleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<DscsnSchedule> findDscsnSchedule(String startDay, String endDay, String dscsnType, String empNo) {
        QDscsnSchedule dscsnSchedule = QDscsnSchedule.dscsnSchedule;

        // 동적 where 조립
        BooleanBuilder builder = new BooleanBuilder();

        //날짜 범위
        if(StringUtils.hasText(startDay) && StringUtils.hasText(endDay)){
            builder.and(dscsnSchedule.possibleDate.between(startDay,endDay));
        }
        //교원번호
        if(StringUtils.hasText(empNo)){
            builder.and(dscsnSchedule.employee.empNo.eq(empNo));
        }
        //상담유형
        if(StringUtils.hasText(dscsnType)){
            builder.and(dscsnSchedule.dscsnDtId.startsWith(dscsnType));
        }

        List<DscsnSchedule> dscsnKindList = queryFactory
                .selectFrom(dscsnSchedule)
                .where(builder)
                .fetch();

        return dscsnKindList;
    }
}
