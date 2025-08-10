package com.secondprojinitiumback.user.student.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudentStatusInfo is a Querydsl query type for StudentStatusInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudentStatusInfo extends EntityPathBase<StudentStatusInfo> {

    private static final long serialVersionUID = 1112378291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudentStatusInfo studentStatusInfo = new QStudentStatusInfo("studentStatusInfo");

    public final QStudentStatusId id;

    public final com.secondprojinitiumback.common.domain.QCommonCode statusCode;

    public final StringPath studentStatusName = createString("studentStatusName");

    public QStudentStatusInfo(String variable) {
        this(StudentStatusInfo.class, forVariable(variable), INITS);
    }

    public QStudentStatusInfo(Path<? extends StudentStatusInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudentStatusInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudentStatusInfo(PathMetadata metadata, PathInits inits) {
        this(StudentStatusInfo.class, metadata, inits);
    }

    public QStudentStatusInfo(Class<? extends StudentStatusInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QStudentStatusId(forProperty("id")) : null;
        this.statusCode = inits.isInitialized("statusCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("statusCode"), inits.get("statusCode")) : null;
    }

}

