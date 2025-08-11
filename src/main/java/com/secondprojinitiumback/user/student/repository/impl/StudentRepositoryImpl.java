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

    // 코드 상수 정의
    private static final String DEPT_DIVISION_CODE_GROUP = "CO0003";
    private static final String STUDENT_STATUS_CODE_GROUP = "SL0030";
    private static final String GENDER_CODE_GROUP = "CO0001";

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
        long total = countQuery.fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    // 기본 쿼리 생성 메서드
    private JPAQuery<Student> createBaseQuery(StudentSearchDto searchDto) {
        QStudent student = QStudent.student;
        QSchoolSubject schoolSubject = QSchoolSubject.schoolSubject;
        QUniversity university = QUniversity.university;
        QStudentStatusInfo studentStatus = QStudentStatusInfo.studentStatusInfo;
        QEmployee advisor = QEmployee.employee;
        QCommonCode gender = new QCommonCode("gender");

        JPAQuery<Student> query = queryFactory
                .selectFrom(student)
                .leftJoin(student.schoolSubject, schoolSubject).fetchJoin()
                .leftJoin(student.school, university).fetchJoin()
                .leftJoin(student.studentStatus, studentStatus).fetchJoin()
                .leftJoin(student.advisor, advisor).fetchJoin()
                .leftJoin(student.gender, gender).fetchJoin();

        return applyConditions(query, searchDto);
    }

    // 전체 개수 조회 쿼리
    private JPAQuery<Long> createCountQuery(StudentSearchDto searchDto) {
        QStudent student = QStudent.student;
        QSchoolSubject schoolSubject = QSchoolSubject.schoolSubject;
        QUniversity university = QUniversity.university;
        QStudentStatusInfo studentStatus = QStudentStatusInfo.studentStatusInfo;
        QEmployee advisor = QEmployee.employee;
        QCommonCode gender = new QCommonCode("gender");

        JPAQuery<Long> query = queryFactory
                .select(student.count())
                .from(student)
                .leftJoin(student.schoolSubject, schoolSubject)
                .leftJoin(student.school, university)
                .leftJoin(student.studentStatus, studentStatus)
                .leftJoin(student.advisor, advisor)
                .leftJoin(student.gender, gender);

        return applyConditions(query, searchDto);
    }

    private <T> JPAQuery<T> applyConditions(JPAQuery<T> query, StudentSearchDto searchDto) {
        return query.where(
                eqStudentNo(searchDto.getStudentNo()),
                containsName(searchDto.getName()),
                eqUniversity(searchDto.getUniversityCode()),
                eqSchoolSubject(searchDto.getSchoolSubjectCode(), searchDto.getSchoolSubjectCodeSe()),
                eqClub(searchDto.getClubCode()),
                eqStatus(searchDto.getStudentStatusCode(), searchDto.getStudentStatusCodeSe()),
                eqGrade(searchDto.getGrade()),
                eqGender(searchDto.getGenderCode(), searchDto.getGenderCodeSe()),
                eqAdvisor(searchDto.getAdvisorId()),
                containsEmail(searchDto.getEmail()),
                betweenAdmissionDate(searchDto.getAdmissionDateFrom(), searchDto.getAdmissionDateTo())
        );
    }

    // === 검색 조건 메서드들 (BooleanExpression) ===

    // 학생번호 검색
    private BooleanExpression eqStudentNo(String studentNo) {
        return StringUtils.hasText(studentNo) ? QStudent.student.studentNo.eq(studentNo) : null;
    }

    // 이름 검색
    private BooleanExpression containsName(String name) {
        return StringUtils.hasText(name) ? QStudent.student.name.containsIgnoreCase(name) : null;
    }

    // 대학 코드 검색
    private BooleanExpression eqUniversity(String universityCode) {
        return StringUtils.hasText(universityCode) ? QStudent.student.school.universityCode.eq(universityCode) : null;
    }

    // 담당 학과 코드 검색 - DTO에서 CodeSe 값 활용
    private BooleanExpression eqSchoolSubject(String schoolSubjectCode, String schoolSubjectCodeSe) {
        if (!StringUtils.hasText(schoolSubjectCode)) {
            return null;
        }

        BooleanExpression condition = QStudent.student.schoolSubject.subjectCode.eq(schoolSubjectCode);

        // CodeSe가 제공되면 사용, 아니면 기본값
        String codeGroup = StringUtils.hasText(schoolSubjectCodeSe) ? schoolSubjectCodeSe : DEPT_DIVISION_CODE_GROUP;
        condition = condition.and(QStudent.student.schoolSubject.deptDivision.id.codeGroup.eq(codeGroup));

        return condition;
    }

    // 동아리 코드 검색
    private BooleanExpression eqClub(String clubCode) {
        return StringUtils.hasText(clubCode) ? QStudent.student.clubCode.eq(clubCode) : null;
    }

    // 학적 상태 코드 검색 - DTO에서 CodeSe 값 활용
    private BooleanExpression eqStatus(String statusCode, String statusCodeSe) {
        if (!StringUtils.hasText(statusCode)) {
            return null;
        }

        BooleanExpression condition = QStudent.student.studentStatus.id.studentStatusCode.eq(statusCode);

        // CodeSe가 제공되면 사용, 아니면 기본값
        String codeGroup = StringUtils.hasText(statusCodeSe) ? statusCodeSe : STUDENT_STATUS_CODE_GROUP;
        condition = condition.and(QStudent.student.studentStatus.id.studentStatusCodeSe.eq(codeGroup));

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
            if (gradeNum < 1 || gradeNum > 6) { // 일반적인 학년 범위
                return null;
            }
            return QStudent.student.grade.eq(grade);
        } catch (NumberFormatException e) {
            return null; // 숫자가 아닌 경우 조건 무시
        }
    }

    // 성별 코드 검색 - DTO에서 CodeSe 값 활용
    private BooleanExpression eqGender(String genderCode, String genderCodeSe) {
        if (!StringUtils.hasText(genderCode)) {
            return null;
        }

        BooleanExpression condition = QStudent.student.gender.id.code.eq(genderCode);

        // CodeSe가 제공되면 사용, 아니면 기본값
        String codeGroup = StringUtils.hasText(genderCodeSe) ? genderCodeSe : GENDER_CODE_GROUP;
        condition = condition.and(QStudent.student.gender.id.codeGroup.eq(codeGroup));

        return condition;
    }

    // 지도교수 ID 검색
    private BooleanExpression eqAdvisor(String advisorId) {
        return StringUtils.hasText(advisorId) ? QStudent.student.advisor.empNo.eq(advisorId) : null;
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

        return QStudent.student.email.containsIgnoreCase(email);
    }

    // 입학일자 범위 검색 - 유효성 검증 추가
    private BooleanExpression betweenAdmissionDate(LocalDate from, LocalDate to) {
        if (from == null && to == null) {
            return null;
        }

        // 날짜 범위 유효성 검증
        if (from != null && to != null && from.isAfter(to)) {
            return null; // 잘못된 날짜 범위인 경우 조건 무시
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