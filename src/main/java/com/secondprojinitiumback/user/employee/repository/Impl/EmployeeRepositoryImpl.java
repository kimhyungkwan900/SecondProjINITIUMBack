package com.secondprojinitiumback.user.employee.repository.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.common.domain.QCommonCode;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.dto.EmployeeSearchDto;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.secondprojinitiumback.common.domain.QSchoolSubject.schoolSubject;
import static com.secondprojinitiumback.user.employee.domain.QEmployee.employee;
import static com.secondprojinitiumback.user.employee.domain.QEmployeeStatusInfo.employeeStatusInfo;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 코드 그룹 상수
    private static final String EMPLOYEE_STATUS_CODE_GROUP = "AM0120";
    private static final String GENDER_CODE_GROUP = "CO0001";

    // Q-타입 인스턴스 선언
    private final QCommonCode qGender = new QCommonCode("gender");

    @Override
    public List<Employee> search(EmployeeSearchDto searchDto) {
        return createBaseQuery(searchDto).fetch();
    }

    @Override
    public Page<Employee> searchPage(EmployeeSearchDto searchDto, Pageable pageable) {
        JPAQuery<Employee> baseQuery = createBaseQuery(searchDto);

        List<Employee> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = createCountQuery(searchDto);
        long total = countQuery.fetchOne() != null ? countQuery.fetchOne() : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    private JPAQuery<Employee> createBaseQuery(EmployeeSearchDto searchDto) {
        JPAQuery<Employee> query = queryFactory
                .selectFrom(employee)
                .leftJoin(employee.schoolSubject, schoolSubject).fetchJoin()
                .leftJoin(employee.employeeStatus, employeeStatusInfo).fetchJoin()
                .leftJoin(employee.gender, qGender).fetchJoin();
        return applyConditions(query, searchDto);
    }

    private JPAQuery<Long> createCountQuery(EmployeeSearchDto searchDto) {
        JPAQuery<Long> query = queryFactory
                .select(employee.count())
                .from(employee)
                .leftJoin(employee.schoolSubject, schoolSubject)
                .leftJoin(employee.employeeStatus, employeeStatusInfo)
                .leftJoin(employee.gender, qGender);
        return applyConditions(query, searchDto);
    }

    private <T> JPAQuery<T> applyConditions(JPAQuery<T> query, EmployeeSearchDto searchDto) {
        return query.where(
                containsEmployeeNo(searchDto.getEmpNo()),
                containsName(searchDto.getName()),
                eqSchoolSubject(searchDto.getSubjectCode()),
                containsStatus(searchDto.getEmployeeStatusCode()),
                containsGender(searchDto.getGenderCode()),
                containsEmail(searchDto.getEmail()),
                containsTel(searchDto.getTel())
        );
    }

    private BooleanExpression containsEmployeeNo(String employeeNo) {
        return StringUtils.hasText(employeeNo) ? employee.empNo.containsIgnoreCase(employeeNo) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? employee.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression eqSchoolSubject(String schoolSubjectCode) {
        return StringUtils.hasText(schoolSubjectCode) ? schoolSubject.subjectCode.eq(schoolSubjectCode) : null;
    }

    private BooleanExpression containsStatus(String statusCode) {
        if (!StringUtils.hasText(statusCode)) {
            return null;
        }
        return employeeStatusInfo.id.employeeStatusCode.containsIgnoreCase(statusCode)
                .and(employeeStatusInfo.id.employeeStatusCodeSe.eq(EMPLOYEE_STATUS_CODE_GROUP));
    }

    private BooleanExpression containsGender(String genderCode) {
        if (!StringUtils.hasText(genderCode)) {
            return null;
        }
        return qGender.id.code.containsIgnoreCase(genderCode)
                .and(qGender.id.codeGroup.eq(GENDER_CODE_GROUP));
    }

    private BooleanExpression containsEmail(String email) {
        return StringUtils.hasText(email) ? employee.email.containsIgnoreCase(email) : null;
    }

    private BooleanExpression containsTel(String tel) {
        return StringUtils.hasText(tel) ? employee.tel.containsIgnoreCase(tel) : null;
    }
}
