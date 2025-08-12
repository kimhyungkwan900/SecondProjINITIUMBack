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

import static com.secondprojinitiumback.user.student.domain.QStudent.student;
import static com.secondprojinitiumback.common.domain.QSchoolSubject.schoolSubject;
import static com.secondprojinitiumback.common.domain.QUniversity.university;
import static com.secondprojinitiumback.user.student.domain.QStudentStatusInfo.studentStatusInfo;
import static com.secondprojinitiumback.user.employee.domain.QEmployee.employee;

@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 코드 상수 정의
    private static final String DEPT_DIVISION_CODE_GROUP = "CO0003";
    private static final String STUDENT_STATUS_CODE_GROUP = "SL0030";
    private static final String GENDER_CODE_GROUP = "CO0001";

    // Q-타입 인스턴스 선언
    private final QStudent qStudent = student;
    private final QSchoolSubject qSchoolSubject = schoolSubject;
    private final QUniversity qUniversity = university;
    private final QStudentStatusInfo qStudentStatusInfo = studentStatusInfo;
    private final QEmployee qEmployee = employee;
    private final QCommonCode qGender = new QCommonCode("gender");
    private final QCommonCode qDeptDivision = new QCommonCode("deptDivision");


    @Override
    public List<Student> search(StudentSearchDto searchDto) {
        return createBaseQuery(searchDto).fetch();
    }

    @Override
    public Page<Student> searchPage(StudentSearchDto searchDto, Pageable pageable) {
        // 콘텐츠 조회 쿼리
        JPAQuery<Student> baseQuery = createBaseQuery(searchDto);

        List<Student> content = baseQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 카운트 조회 쿼리
        JPAQuery<Long> countQuery = createCountQuery(searchDto);
        long total = countQuery.fetchOne() != null ? countQuery.fetchOne() : 0L;

        return new PageImpl<>(content, pageable, total);
    }

    // 기본 쿼리 생성 메서드
    private JPAQuery<Student> createBaseQuery(StudentSearchDto searchDto) {
        JPAQuery<Student> query = queryFactory
                .selectFrom(qStudent)
                .leftJoin(qStudent.schoolSubject, qSchoolSubject).fetchJoin()
                .leftJoin(qSchoolSubject.deptDivision, qDeptDivision).fetchJoin()
                .leftJoin(qStudent.school, qUniversity).fetchJoin()
                .leftJoin(qStudent.studentStatus, qStudentStatusInfo).fetchJoin()
                .leftJoin(qStudent.advisor, qEmployee).fetchJoin()
                .leftJoin(qStudent.gender, qGender).fetchJoin();

        return applyConditions(query, searchDto);
    }

    // 전체 개수 조회 쿼리
    private JPAQuery<Long> createCountQuery(StudentSearchDto searchDto) {
        JPAQuery<Long> query = queryFactory
                .select(qStudent.count())
                .from(qStudent)
                .leftJoin(qStudent.schoolSubject, qSchoolSubject)
                .leftJoin(qSchoolSubject.deptDivision, qDeptDivision)
                .leftJoin(qStudent.school, qUniversity)
                .leftJoin(qStudent.studentStatus, qStudentStatusInfo)
                .leftJoin(qStudent.advisor, qEmployee)
                .leftJoin(qStudent.gender, qGender);

        return applyConditions(query, searchDto);
    }

    private <T> JPAQuery<T> applyConditions(JPAQuery<T> query, StudentSearchDto searchDto) {
        return query.where(
                eqStudentNo(searchDto.getStudentNo()),
                containsName(searchDto.getName()),
                containsUniversity(searchDto.getUniversityCode()),
                eqSchoolSubject(searchDto.getSchoolSubjectCode(), searchDto.getSchoolSubjectCodeSe()),
                containsStatus(searchDto.getStudentStatusCode(), searchDto.getStudentStatusCodeSe()),
                eqGrade(searchDto.getGrade()),
                containsGender(searchDto.getGenderCode(), searchDto.getGenderCodeSe()),
                containsAdvisor(searchDto.getAdvisorId()),
                containsEmail(searchDto.getEmail()),
                betweenAdmissionDate(searchDto.getAdmissionDateFrom(), searchDto.getAdmissionDateTo())
        );
    }

    // === 검색 조건 메서드들 (BooleanExpression) ===

    // 학생번호 검색
    private BooleanExpression eqStudentNo(String studentNo) {
        if (!StringUtils.hasText(studentNo)) {
            return null;
        }
        return qStudent.studentNo.contains(studentNo);
    }

    // 이름 검색
    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? qStudent.name.containsIgnoreCase(name) : null;
    }

    // 대학 코드 검색
    private BooleanExpression containsUniversity(String universityCode) {
        return StringUtils.hasText(universityCode) ? qStudent.school.universityCode.containsIgnoreCase(universityCode) : null;
    }

    // 담당 학과 코드 검색 - DTO에서 CodeSe 값 활용
    private BooleanExpression eqSchoolSubject(String schoolSubjectCode, String schoolSubjectCodeSe) {
        if (!StringUtils.hasText(schoolSubjectCode)) {
            return null;
        }

        BooleanExpression condition = qStudent.schoolSubject.subjectCode.eq(schoolSubjectCode);

        // CodeSe가 제공되면 사용, 아니면 기본값
        String codeGroup = StringUtils.hasText(schoolSubjectCodeSe) ? schoolSubjectCodeSe : DEPT_DIVISION_CODE_GROUP;
        condition = condition.and(qDeptDivision.id.codeGroup.eq(codeGroup));

        return condition;
    }

    // 학적 상태 코드 검색 - DTO에서 CodeSe 값 활용
    private BooleanExpression containsStatus(String statusCode, String statusCodeSe) {
        if (!StringUtils.hasText(statusCode)) {
            return null;
        }

        BooleanExpression condition = qStudentStatusInfo.id.studentStatusCode.containsIgnoreCase(statusCode);

        // CodeSe가 제공되면 사용, 아니면 기본값
        String codeGroup = StringUtils.hasText(statusCodeSe) ? statusCodeSe : STUDENT_STATUS_CODE_GROUP;
        condition = condition.and(qStudentStatusInfo.id.studentStatusCodeSe.eq(codeGroup));

        return condition;
    }

    // 학년 검색 - 숫자 범위 검증 추가
    private BooleanExpression eqGrade(String grade) {
        if (!StringUtils.hasText(grade)) {
            return null;
        }

        // 학년이 숫자인지 검증
        try {
            int gradeNum = Integer.parseInt(grade);
            if (gradeNum < 1 || gradeNum > 4) {
                return null;
            }
            return qStudent.grade.eq(grade);
        } catch (NumberFormatException e) {
            return null; // 숫자가 아닌 경우 조건 무시
        }
    }

    // 성별 코드 검색 - DTO에서 CodeSe 값 활용
    private BooleanExpression containsGender(String genderCode, String genderCodeSe) {
        if (!StringUtils.hasText(genderCode)) {
            return null;
        }

        BooleanExpression condition = qGender.id.code.containsIgnoreCase(genderCode);

        // CodeSe가 제공되면 사용, 아니면 기본값
        String codeGroup = StringUtils.hasText(genderCodeSe) ? genderCodeSe : GENDER_CODE_GROUP;
        condition = condition.and(qGender.id.codeGroup.eq(codeGroup));

        return condition;
    }

    // 지도교수 ID 검색
    private BooleanExpression containsAdvisor(String advisorId) {
        return StringUtils.hasText(advisorId) ? qStudent.advisor.empNo.containsIgnoreCase(advisorId) : null;
    }

    // 이메일 검색 - 이메일 형식 간단 검증 추가
    private BooleanExpression containsEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }

        // 기본적인 이메일 형식 검증 (@ 포함 여부만 체크)
        if (!email.contains("@")) {
            return null;
        }

        return qStudent.email.containsIgnoreCase(email);
    }

    // 입학일자 범위 검색 - 유효성 검증 추가
    private BooleanExpression betweenAdmissionDate(LocalDate from, LocalDate to) {
        if (from == null && to == null) {
            return null;
        }

        if (from != null && to != null && from.isAfter(to)) {
            return null;
        }

        if (from != null && to != null) {
            return qStudent.admissionDate.between(from, to);
        }
        if (from != null) {
            return qStudent.admissionDate.goe(from);
        }
        return qStudent.admissionDate.loe(to);
    }
}