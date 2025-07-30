package com.secondprojinitiumback.user.student.repository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.secondprojinitiumback.common.domain.QSchoolSubject;
import com.secondprojinitiumback.user.student.domain.QStudent;
import com.secondprojinitiumback.user.student.domain.Student;
import com.secondprojinitiumback.user.student.dto.StudentSearchDto;
import com.secondprojinitiumback.user.student.repository.StudentRepositoryCustom;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class StudentRepositoryImpl implements StudentRepositoryCustom {
    // JPAQueryFactory를 사용하기 위해 EntityManager를 주입받습니다.
    private final JPAQueryFactory queryFactory;

    // 생성자 주입을 통해 EntityManager를 받아옵니다.
    public StudentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    // 검색 메서드 구현
    @Override
    public List<Student> search(StudentSearchDto cond) {
        // Q 클래스들을 사용하여 쿼리를 작성합니다.
        QStudent student = QStudent.student;
        // QSchoolSubject는 SchoolSubject 엔티티에 대한 Q 클래스입니다.
        QSchoolSubject subject = QSchoolSubject.schoolSubject;
        // 쿼리 팩토리를 사용하여 학생 정보를 조회합니다.
        return queryFactory
                .selectFrom(student)
                .leftJoin(student.schoolSubject, subject).fetchJoin()
                .where(
                        eqStudentNo(cond.getStudentNo()),
                        eqName(cond.getName()),
                        eqDepartment(cond.getSchoolSubjectCode()),
                        eqStatus(cond.getStudentStatusCode())
                )
                .fetch();
    }

    // 페이지네이션을 적용한 검색 메서드 구현
    @Override
    // 검색 조건에 맞는 학생 정보를 페이지 단위로 조회합니다.
    public Page<Student> searchPage(StudentSearchDto cond, Pageable pageable) {
        // Q 클래스들을 사용하여 쿼리를 작성합니다.
        QStudent student = QStudent.student;
        // QSchoolSubject는 SchoolSubject 엔티티에 대한 Q 클래스입니다.
        QSchoolSubject subject = QSchoolSubject.schoolSubject;
        // 콘텐츠 조회 쿼리
        List<Student> content = queryFactory
                .selectFrom(student)
                .leftJoin(student.schoolSubject, subject).fetchJoin()
                .where(
                        eqStudentNo(cond.getStudentNo()),
                        eqName(cond.getName()),
                        eqDepartment(cond.getSchoolSubjectCode()),
                        eqStatus(cond.getStudentStatusCode())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        // 카운트 조회 쿼리
        JPAQuery<Long> countQuery = queryFactory
                .select(student.count())
                .from(student)
                .where(
                        eqStudentNo(cond.getStudentNo()),
                        eqName(cond.getName()),
                        eqDepartment(cond.getSchoolSubjectCode()),
                        eqStatus(cond.getStudentStatusCode())
                );
        // 카운트 쿼리를 실행하여 총 레코드 수를 가져옵니다.
        long total = countQuery.fetchOne();
        // 페이지 정보를 포함한 Page 객체를 생성하여 반환합니다.
        return new PageImpl<>(content, pageable, total);
    }

    // 검색 조건 메서드들
    // 학번
    private BooleanExpression eqStudentNo(String studentNo) {
        return (studentNo == null || studentNo.isBlank()) ? null : QStudent.student.studentNo.eq(studentNo);
    }
    // 이름
    private BooleanExpression eqName(String name) {
        return (name == null || name.isBlank()) ? null : QStudent.student.name.containsIgnoreCase(name);
    }
    // 학과(부서) 코드
    private BooleanExpression eqDepartment(String code) {
        return (code == null || code.isBlank()) ? null : QStudent.student.schoolSubject.subjectCode.eq(code);
    }
    // 학생 상태 코드
    private BooleanExpression eqStatus(String code) {
        return (code == null || code.isBlank()) ? null : QStudent.student.studentStatus.statusCode.eq(code);
    }
}
