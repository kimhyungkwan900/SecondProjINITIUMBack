package com.secondprojinitiumback.user.employee.repository.Impl;

import static com.secondprojinitiumback.user.employee.domain.QEmployee.employee;
import static com.secondprojinitiumback.common.domain.QSchoolSubject.schoolSubject;
import static com.secondprojinitiumback.user.employee.domain.QEmployeeStatusInfo.employeeStatusInfo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.common.domain.QCommonCode;
import com.secondprojinitiumback.common.domain.QSchoolSubject;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.domain.QEmployee;
import com.secondprojinitiumback.user.employee.domain.QEmployeeStatusInfo;
import com.secondprojinitiumback.user.employee.dto.EmployeeSearchDto;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // Q-타입 인스턴스 선언
    private final QEmployee qEmployee = employee;
    private final QSchoolSubject qSchoolSubject = schoolSubject;
    private final QEmployeeStatusInfo qEmployeeStatusInfo = employeeStatusInfo;
    private final QCommonCode qGender = new QCommonCode("gender");

    @Override
    public List<Employee> search(EmployeeSearchDto searchDto) {
        return createBaseQuery(searchDto).fetch();
    }

    @Override
    public Page<Employee> searchPage(EmployeeSearchDto searchDto, Pageable pageable) {
        // 기본 쿼리 생성
        JPAQuery<Employee> baseQuery = createBaseQuery(searchDto);

        // 페이징 처리
        List<Employee> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회 쿼리 생성
        JPAQuery<Long> countQuery = createCountQuery(searchDto);
        // 전체 개수 조회
        long total = countQuery.fetchOne() != null ? countQuery.fetchOne() : 0L;

        // Page 객체 생성
        return new PageImpl<>(content, pageable, total);
    }

    private JPAQuery<Employee> createBaseQuery(EmployeeSearchDto searchDto) {
        return queryFactory
                .selectFrom(qEmployee)
                .leftJoin(qEmployee.schoolSubject, qSchoolSubject).fetchJoin()
                .leftJoin(qEmployee.employeeStatus, qEmployeeStatusInfo).fetchJoin()
                .leftJoin(qEmployee.gender, qGender).fetchJoin()
                .where(
                        eqEmployeeNo(searchDto.getEmpNo()),
                        containsName(searchDto.getName()),
                        eqSchoolSubject(searchDto.getSubjectCode()),
                        eqStatus(searchDto.getEmployeeStatusCode()),
                        eqGender(searchDto.getGenderCode()),
                        containsEmail(searchDto.getEmail()),
                        eqTel(searchDto.getTel())
                );
    }

    private JPAQuery<Long> createCountQuery(EmployeeSearchDto searchDto) {
        return queryFactory
                .select(qEmployee.count())
                .from(qEmployee)
                .where(
                        eqEmployeeNo(searchDto.getEmpNo()),
                        containsName(searchDto.getName()),
                        eqSchoolSubject(searchDto.getSubjectCode()),
                        eqStatus(searchDto.getEmployeeStatusCode()),
                        eqGender(searchDto.getGenderCode()),
                        containsEmail(searchDto.getEmail()),
                        eqTel(searchDto.getTel())
                );
    }

    // === 검색 조건 메서드들 (BooleanExpression) ===

    // 교번/사번 검색
    private BooleanExpression eqEmployeeNo(String employeeNo) {
        return StringUtils.hasText(employeeNo) ? qEmployee.empNo.eq(employeeNo) : null;
    }

    // 이름 검색
    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? qEmployee.name.containsIgnoreCase(name) : null;
    }

    // 담당 과목(부서) 코드 검색
    private BooleanExpression eqSchoolSubject(String schoolSubjectCode) {
        return StringUtils.hasText(schoolSubjectCode) ? qSchoolSubject.subjectCode.eq(schoolSubjectCode) : null;
    }

    // 상태 코드 검색
    private BooleanExpression eqStatus(String statusCode) {
        return StringUtils.hasText(statusCode) ? qEmployeeStatusInfo.id.employeeStatusCode.eq(statusCode) : null;
    }

    // 성별 코드 검색
    private BooleanExpression eqGender(String genderCode) {
        return StringUtils.hasText(genderCode) ? qGender.id.code.eq(genderCode) : null;
    }

    // 이메일 검색
    private BooleanExpression containsEmail(String email) {
        return StringUtils.hasText(email) ? qEmployee.email.containsIgnoreCase(email) : null;
    }

    // 전화번호 검색
    private BooleanExpression eqTel(String tel) {
        return StringUtils.hasText(tel) ? qEmployee.tel.eq(tel) : null;
    }
}