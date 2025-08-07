package com.secondprojinitiumback.user.student.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = 1701800851L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudent student = new QStudent("student");

    public final DatePath<java.time.LocalDate> admissionDate = createDate("admissionDate", java.time.LocalDate.class);

    public final com.secondprojinitiumback.user.employee.domain.QEmployee advisor;

    public final com.secondprojinitiumback.common.bank.domain.QBankAccount bankAccount;

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath clubCode = createString("clubCode");

    public final StringPath email = createString("email");

    public final com.secondprojinitiumback.common.domain.QCommonCode gender;

    public final StringPath grade = createString("grade");

    public final com.secondprojinitiumback.common.security.domain.QLoginInfo loginInfo;

    public final StringPath name = createString("name");

    public final com.secondprojinitiumback.common.domain.QUniversity school;

    public final com.secondprojinitiumback.common.domain.QSchoolSubject schoolSubject;

    public final StringPath studentNo = createString("studentNo");

    public final QStudentStatusInfo studentStatus;

    public QStudent(String variable) {
        this(Student.class, forVariable(variable), INITS);
    }

    public QStudent(Path<? extends Student> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudent(PathMetadata metadata, PathInits inits) {
        this(Student.class, metadata, inits);
    }

    public QStudent(Class<? extends Student> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.advisor = inits.isInitialized("advisor") ? new com.secondprojinitiumback.user.employee.domain.QEmployee(forProperty("advisor"), inits.get("advisor")) : null;
        this.bankAccount = inits.isInitialized("bankAccount") ? new com.secondprojinitiumback.common.bank.domain.QBankAccount(forProperty("bankAccount"), inits.get("bankAccount")) : null;
        this.gender = inits.isInitialized("gender") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("gender"), inits.get("gender")) : null;
        this.loginInfo = inits.isInitialized("loginInfo") ? new com.secondprojinitiumback.common.security.domain.QLoginInfo(forProperty("loginInfo")) : null;
        this.school = inits.isInitialized("school") ? new com.secondprojinitiumback.common.domain.QUniversity(forProperty("school")) : null;
        this.schoolSubject = inits.isInitialized("schoolSubject") ? new com.secondprojinitiumback.common.domain.QSchoolSubject(forProperty("schoolSubject"), inits.get("schoolSubject")) : null;
        this.studentStatus = inits.isInitialized("studentStatus") ? new QStudentStatusInfo(forProperty("studentStatus"), inits.get("studentStatus")) : null;
    }

}

