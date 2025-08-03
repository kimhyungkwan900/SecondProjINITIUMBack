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

    public final com.secondprojinitiumback.common.domain.base.QBaseEntity _super = new com.secondprojinitiumback.common.domain.base.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath createdIp = _super.createdIp;

    //inherited
    public final NumberPath<Long> createdProgramId = _super.createdProgramId;

    public final QCommonCode deptDivision;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedIp = _super.modifiedIp;

    //inherited
    public final NumberPath<Long> modifiedProgramId = _super.modifiedProgramId;

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

