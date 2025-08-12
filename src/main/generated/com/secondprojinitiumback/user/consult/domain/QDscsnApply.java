package com.secondprojinitiumback.user.consult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDscsnApply is a Querydsl query type for DscsnApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDscsnApply extends EntityPathBase<DscsnApply> {

    private static final long serialVersionUID = -1161235272L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDscsnApply dscsnApply = new QDscsnApply("dscsnApply");

    public final StringPath dscsnApplyCn = createString("dscsnApplyCn");

    public final StringPath dscsnApplyId = createString("dscsnApplyId");

    public final QDscsnSchedule dscsnDt;

    public final QDscsnKind dscsnKind;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public final StringPath studentTelno = createString("studentTelno");

    public QDscsnApply(String variable) {
        this(DscsnApply.class, forVariable(variable), INITS);
    }

    public QDscsnApply(Path<? extends DscsnApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDscsnApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDscsnApply(PathMetadata metadata, PathInits inits) {
        this(DscsnApply.class, metadata, inits);
    }

    public QDscsnApply(Class<? extends DscsnApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dscsnDt = inits.isInitialized("dscsnDt") ? new QDscsnSchedule(forProperty("dscsnDt"), inits.get("dscsnDt")) : null;
        this.dscsnKind = inits.isInitialized("dscsnKind") ? new QDscsnKind(forProperty("dscsnKind")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

