package com.secondprojinitiumback.user.consult.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.user.consult.domain.DscsnInfo;
import com.secondprojinitiumback.user.consult.domain.QDscsnInfo;
import com.secondprojinitiumback.user.consult.dto.common.DscsnInfoSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class DscsnInfoRepositoryCustomImpl implements  DscsnInfoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    //상담분야, 상담 시작일, 상담 종료일, 상담상태, 연도, 학기, 상담항목, 학번, 성명, 학적상태, 소속(학과)

    @Override
    public Page<DscsnInfo> getDscsnInfoPageByCondition(DscsnInfoSearchDto dscsnInfoSearchDto, Pageable pageable) {
        QDscsnInfo dscsnInfo = QDscsnInfo.dscsnInfo;    //자동 생성된 Q클래스

        // 동적 where절 조립
        BooleanBuilder builder = new BooleanBuilder();

        // 사용자 유형에 따라 쿼리문 추가
        switch(dscsnInfoSearchDto.getUserType()){
            case "S":
                builder.and(dscsnInfo.dscsnApply.student.studentNo.eq(dscsnInfoSearchDto.getSerialNo()));
                break;
            case "E":
                builder.and(dscsnInfo.dscsnApply.dscsnDt.employee.empNo.eq(dscsnInfoSearchDto.getSerialNo()));
                break;
            case "A":
                // 관리자는 모든 상담현황 조회 가능하므로 추가하는 쿼리문 X
                break;
        }

        //상담분야
        if(StringUtils.hasText(dscsnInfoSearchDto.getDscsnType())){
            builder.and(dscsnInfo.dscsnApply.dscsnDt.dscsnDtId.startsWith(dscsnInfoSearchDto.getDscsnType()));
        }
        //상담일
        if(StringUtils.hasText(dscsnInfoSearchDto.getStartDate()) && StringUtils.hasText(dscsnInfoSearchDto.getEndDate())){
            builder.and(dscsnInfo.dscsnApply.dscsnDt.possibleDate.between(dscsnInfoSearchDto.getStartDate(), dscsnInfoSearchDto.getEndDate()));
        }
        //상담상태
        if(StringUtils.hasText(dscsnInfoSearchDto.getDscsnStatus())){
            builder.and(dscsnInfo.dscsnStatus.eq(dscsnInfoSearchDto.getDscsnStatus()));
        }
        //연도,학기
        if(StringUtils.hasText(dscsnInfoSearchDto.getDscsnType())){
            String semesterStart = dscsnInfoSearchDto.getYear() + dscsnInfoSearchDto.getStartMonth()
            String semesterEnd = dscsnInfoSearchDto.getYear() + dscsnInfoSearchDto.getEndMonth();

            builder.and(dscsnInfo.dscsnApply.dscsnDt.possibleDate.between(semesterStart, semesterEnd));
        }
        //상담항목
        if(StringUtils.hasText(dscsnInfoSearchDto.getDscsnKindId())){
            builder.and(dscsnInfo.dscsnApply.dscsnKind.dscsnKindId.eq(dscsnInfoSearchDto.getDscsnKindId()));
        }
        //학번
        if(StringUtils.hasText(dscsnInfoSearchDto.getStudentNo())){
            builder.and(dscsnInfo.dscsnApply.student.studentNo.eq(dscsnInfoSearchDto.getStudentNo()));
        }
        //성명
        if(StringUtils.hasText(dscsnInfoSearchDto.getStudentName())){
            builder.and(dscsnInfo.dscsnApply.student.name.eq(dscsnInfoSearchDto.getStudentName()));
        }
        //학적상태
        if(StringUtils.hasText(dscsnInfoSearchDto.getStudentStatus())){ //학적상태 코드 값이 어떻게 되는지 질문
            builder.and(dscsnInfo.dscsnApply.student.studentStatus.statusCode.codeName.eq(dscsnInfoSearchDto.getStudentStatus()));
        }
        //소속(학과)
        if(StringUtils.hasText(dscsnInfoSearchDto.getDepart())){
            builder.and(dscsnInfo.dscsnApply.student.schoolSubject.subjectCode.eq(dscsnInfoSearchDto.getDepart()));
        }

        //쿼리문 실행
        List<DscsnInfo> searchResult = queryFactory
                .selectFrom(dscsnInfo)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(dscsnInfo.count())
                .from(dscsnInfo)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(searchResult, pageable, total);
    }
}
