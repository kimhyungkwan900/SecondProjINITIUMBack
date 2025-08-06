package com.secondprojinitiumback.user.student.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentStatusId is a Querydsl query type for StudentStatusId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStudentStatusId extends BeanPath<StudentStatusId> {

    private static final long serialVersionUID = -1768672928L;

    public static final QStudentStatusId studentStatusId = new QStudentStatusId("studentStatusId");

    public final StringPath studentStatusCode = createString("studentStatusCode");

    public final StringPath studentStatusCodeSe = createString("studentStatusCodeSe");

    public QStudentStatusId(String variable) {
        super(StudentStatusId.class, forVariable(variable));
    }

    public QStudentStatusId(Path<? extends StudentStatusId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentStatusId(PathMetadata metadata) {
        super(StudentStatusId.class, metadata);
    }

}

