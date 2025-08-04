package com.secondprojinitiumback.user.student.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.common.domain.QCommonCode;
import com.secondprojinitiumback.common.domain.QSchoolSubject;
import com.secondprojinitiumback.common.domain.QUniversity;
import com.secondprojinitiumback.user.employee.domain.QEmployee;
import com.secondprojinitiumback.user.student.domain.QStudent;
import com.secondprojinitiumback.user.student.domain.QStudentStatusInfo;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.dto.StudentSearchDto;
import com.secondprojinitiumback.user.student.repository.StudentRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Student> search(StudentSearchDto searchDto) {
        return createBaseQuery(searchDto).fetch();
    }

    @Override
    public Page<Student> searchPage(StudentSearchDto searchDto, Pageable pageable) {
        // 콘텐츠 조회 쿼리 (재사용)
        JPAQuery<Student> baseQuery = createBaseQuery(searchDto);

        List<Student> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트 조회 쿼리 (재사용)
        JPAQuery<Long> countQuery = createCountQuery(searchDto);
        long total = countQuery.fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private JPAQuery<Student> createBaseQuery(StudentSearchDto searchDto) {
        QStudent student = QStudent.student;
        QSchoolSubject schoolSubject = QSchoolSubject.schoolSubject;
        QUniversity university = QUniversity.university;
        QStudentStatusInfo studentStatus = QStudentStatusInfo.studentStatusInfo;
        QEmployee advisor = QEmployee.employee;
        QCommonCode gender = new QCommonCode("gender");

        return queryFactory
                .selectFrom(student)
                .leftJoin(student.schoolSubject, schoolSubject).fetchJoin()
                .leftJoin(student.school, university).fetchJoin()
                .leftJoin(student.studentStatus, studentStatus).fetchJoin()
                .leftJoin(student.advisor, advisor).fetchJoin()
                .leftJoin(student.gender, gender).fetchJoin()
                .where(
                        eqStudentNo(searchDto.getStudentNo()),
                        containsName(searchDto.getName()),
                        eqUniversity(searchDto.getUniversityCode()),
                        eqSchoolSubject(searchDto.getSchoolSubjectCode()),
                        eqClub(searchDto.getClubCode()),
                        eqStatus(searchDto.getStudentStatusCode()),
                        eqGrade(searchDto.getGrade()),
                        eqGender(searchDto.getGenderCode()),
                        eqAdvisor(searchDto.getAdvisorId()),
                        containsEmail(searchDto.getEmail()),
                        betweenAdmissionDate(searchDto.getAdmissionDateFrom(), searchDto.getAdmissionDateTo())
                );
    }

    private JPAQuery<Long> createCountQuery(StudentSearchDto searchDto) {
        QStudent student = QStudent.student;

        return queryFactory
                .select(student.count())
                .from(student)
                .where(
                        eqStudentNo(searchDto.getStudentNo()),
                        containsName(searchDto.getName()),
                        eqUniversity(searchDto.getUniversityCode()),
                        eqSchoolSubject(searchDto.getSchoolSubjectCode()),
                        eqClub(searchDto.getClubCode()),
                        eqStatus(searchDto.getStudentStatusCode()),
                        eqGrade(searchDto.getGrade()),
                        eqGender(searchDto.getGenderCode()),
                        eqAdvisor(searchDto.getAdvisorId()),
                        containsEmail(searchDto.getEmail()),
                        betweenAdmissionDate(searchDto.getAdmissionDateFrom(), searchDto.getAdmissionDateTo())
                );
    }

    // === 검색 조건 메서드들 (BooleanExpression) ===

    private BooleanExpression eqStudentNo(String studentNo) {
        return StringUtils.hasText(studentNo) ? QStudent.student.studentNo.eq(studentNo) : null;
    }

    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? QStudent.student.name.containsIgnoreCase(name) : null;
    }

    private BooleanExpression eqUniversity(String universityCode) {
        return StringUtils.hasText(universityCode) ? QStudent.student.school.universityCode.eq(universityCode) : null;
    }

    private BooleanExpression eqSchoolSubject(String schoolSubjectCode) {
        return StringUtils.hasText(schoolSubjectCode) ? QStudent.student.schoolSubject.subjectCode.eq(schoolSubjectCode) : null;
    }

    private BooleanExpression eqClub(String clubCode) {
        return StringUtils.hasText(clubCode) ? QStudent.student.clubCode.eq(clubCode) : null;
    }

    private BooleanExpression eqStatus(String statusCode) {
        return StringUtils.hasText(statusCode) ? QStudent.student.studentStatus.studentStatusCode.eq(statusCode) : null;
    }

    private BooleanExpression eqGrade(String grade) {
        return StringUtils.hasText(grade) ? QStudent.student.grade.eq(grade) : null;
    }

    private BooleanExpression eqGender(String genderCode) {
        return StringUtils.hasText(genderCode) ? QStudent.student.gender.id.code.eq(genderCode) : null;
    }

    private BooleanExpression eqAdvisor(String advisorId) {
        return StringUtils.hasText(advisorId) ? QStudent.student.advisor.empNo.eq(advisorId) : null;
    }

    private BooleanExpression containsEmail(String email) {
        return StringUtils.hasText(email) ? QStudent.student.email.containsIgnoreCase(email) : null;
    }

    private BooleanExpression betweenAdmissionDate(LocalDate from, LocalDate to) {
        if (from == null && to == null) {
            return null;
        }
        if (from != null && to != null) {
            return QStudent.student.admissionDate.between(from, to);
        }
        if (from != null) {
            return QStudent.student.admissionDate.goe(from);
        }
        return QStudent.student.admissionDate.loe(to);
    }
}