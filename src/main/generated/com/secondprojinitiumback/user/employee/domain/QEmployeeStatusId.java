package com.secondprojinitiumback.user.employee.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmployeeStatusId is a Querydsl query type for EmployeeStatusId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEmployeeStatusId extends BeanPath<EmployeeStatusId> {

    private static final long serialVersionUID = -1269166060L;

    public static final QEmployeeStatusId employeeStatusId = new QEmployeeStatusId("employeeStatusId");

    public final StringPath employeeStatusCode = createString("employeeStatusCode");

    public final StringPath employeeStatusCodeSe = createString("employeeStatusCodeSe");

    public QEmployeeStatusId(String variable) {
        super(EmployeeStatusId.class, forVariable(variable));
    }

    public QEmployeeStatusId(Path<? extends EmployeeStatusId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployeeStatusId(PathMetadata metadata) {
        super(EmployeeStatusId.class, metadata);
    }

}

