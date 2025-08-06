package com.secondprojinitiumback.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolSubject is a Querydsl query type for SchoolSubject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolSubject extends EntityPathBase<SchoolSubject> {

    private static final long serialVersionUID = 393638525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchoolSubject schoolSubject = new QSchoolSubject("schoolSubject");

    public final QCommonCode deptDivision;

    public final StringPath subjectCode = createString("subjectCode");

    public final StringPath subjectName = createString("subjectName");

    public QSchoolSubject(String variable) {
        this(SchoolSubject.class, forVariable(variable), INITS);
    }

    public QSchoolSubject(Path<? extends SchoolSubject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchoolSubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchoolSubject(PathMetadata metadata, PathInits inits) {
        this(SchoolSubject.class, metadata, inits);
    }

    public QSchoolSubject(Class<? extends SchoolSubject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deptDivision = inits.isInitialized("deptDivision") ? new QCommonCode(forProperty("deptDivision"), inits.get("deptDivision")) : null;
    }

}

