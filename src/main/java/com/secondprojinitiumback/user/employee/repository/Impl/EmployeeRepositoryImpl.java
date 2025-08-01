package com.secondprojinitiumback.user.employee.repository.Impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.common.domain.QSchoolSubject;
import com.secondprojinitiumback.user.employee.domain.Employee;
import com.secondprojinitiumback.user.employee.domain.QEmployee;
import com.secondprojinitiumback.user.employee.dto.EmployeeSearchDto;
import com.secondprojinitiumback.user.employee.repository.EmployeeRepositoryCustom;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@AllArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    // JPAQueryFactory를 사용하기 위한 EntityManager 주입.
    private final JPAQueryFactory queryFactory;

    // 검색 메서드 구현
    @Override
    public List<Employee> search(EmployeeSearchDto employeeSearchDto) {
        QEmployee employee = QEmployee.employee;
        QSchoolSubject subject = QSchoolSubject.schoolSubject;

        return queryFactory
                .selectFrom(employee)
                .leftJoin(employee.schoolSubject, subject).fetchJoin()
                .where(
                        eqEmployeeNo(employeeSearchDto.getEmployeeNo()),
                        eqName(employeeSearchDto.getName()),
                        eqSchoolSubjectCode(employeeSearchDto.getSchoolSubject()),
                        eqStatusCode(employeeSearchDto.getStatusCode())
                )
                .fetch();
    }

    @Override
    public Page<Employee> searchPage(EmployeeSearchDto employeeSearchDto, Pageable pageable) {
        QEmployee employee = QEmployee.employee;
        QSchoolSubject subject = QSchoolSubject.schoolSubject;

        List<Employee> content = queryFactory
                .selectFrom(employee)
                .leftJoin(employee.schoolSubject, subject).fetchJoin()
                .where(
                        eqEmployeeNo(employeeSearchDto.getEmployeeNo()),
                        eqName(employeeSearchDto.getName()),
                        eqSchoolSubjectCode(employeeSearchDto.getSchoolSubject()),
                        eqStatusCode(employeeSearchDto.getStatusCode())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(employee.count())
                .from(employee)
                .where(
                        eqEmployeeNo(employeeSearchDto.getEmployeeNo()),
                        eqName(employeeSearchDto.getName()),
                        eqSchoolSubjectCode(employeeSearchDto.getSchoolSubject()),
                        eqStatusCode(employeeSearchDto.getStatusCode())
                );

        long total = countQuery.fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    // 검색 조건 메서드
    // 교번/사번
    private BooleanExpression eqEmployeeNo(String employeeNo) {
        return (employeeNo == null || employeeNo.isBlank()) ? null : QEmployee.employee.empNo.eq(employeeNo);
    }
    // 이름
    private BooleanExpression eqName(String name) {
        return (name == null || name.isBlank()) ? null : QEmployee.employee.name.eq(name);
    }
    // 소속 부서(학과) 코드
    private BooleanExpression eqSchoolSubjectCode(String schoolSubjectCode) {
        return (schoolSubjectCode == null || schoolSubjectCode.isBlank()) ? null : QEmployee.employee.schoolSubject.subjectCode.eq(schoolSubjectCode);
    }
    // 상태 코드    TODO: 재직 상태코드 가져오기
    private BooleanExpression eqStatusCode(String statusCode) {
        return null;
    }

}
