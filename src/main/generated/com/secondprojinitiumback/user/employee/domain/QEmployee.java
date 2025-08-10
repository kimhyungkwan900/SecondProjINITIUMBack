package com.secondprojinitiumback.user.employee.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = -922141113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployee employee = new QEmployee("employee");

    public final com.secondprojinitiumback.common.bank.domain.QBankAccount bankAccount;

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath email = createString("email");

    public final QEmployeeStatusInfo employeeStatus;

    public final StringPath empNo = createString("empNo");

    public final com.secondprojinitiumback.common.domain.QCommonCode gender;

    public final com.secondprojinitiumback.common.security.domain.QLoginInfo loginInfo;

    public final StringPath name = createString("name");

    public final com.secondprojinitiumback.common.domain.QSchoolSubject schoolSubject;

    public final StringPath tel = createString("tel");

    public QEmployee(String variable) {
        this(Employee.class, forVariable(variable), INITS);
    }

    public QEmployee(Path<? extends Employee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployee(PathMetadata metadata, PathInits inits) {
        this(Employee.class, metadata, inits);
    }

    public QEmployee(Class<? extends Employee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bankAccount = inits.isInitialized("bankAccount") ? new com.secondprojinitiumback.common.bank.domain.QBankAccount(forProperty("bankAccount"), inits.get("bankAccount")) : null;
        this.employeeStatus = inits.isInitialized("employeeStatus") ? new QEmployeeStatusInfo(forProperty("employeeStatus"), inits.get("employeeStatus")) : null;
        this.gender = inits.isInitialized("gender") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("gender"), inits.get("gender")) : null;
        this.loginInfo = inits.isInitialized("loginInfo") ? new com.secondprojinitiumback.common.security.domain.QLoginInfo(forProperty("loginInfo")) : null;
        this.schoolSubject = inits.isInitialized("schoolSubject") ? new com.secondprojinitiumback.common.domain.QSchoolSubject(forProperty("schoolSubject"), inits.get("schoolSubject")) : null;
    }

}

