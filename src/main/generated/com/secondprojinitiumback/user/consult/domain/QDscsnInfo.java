package com.secondprojinitiumback.user.consult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDscsnInfo is a Querydsl query type for DscsnInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDscsnInfo extends EntityPathBase<DscsnInfo> {

    private static final long serialVersionUID = -868507100L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDscsnInfo dscsnInfo = new QDscsnInfo("dscsnInfo");

    public final QDscsnApply dscsnApply;

    public final StringPath dscsnInfoId = createString("dscsnInfoId");

    public final StringPath dscsnReleaseYn = createString("dscsnReleaseYn");

    public final StringPath dscsnResultCn = createString("dscsnResultCn");

    public final StringPath dscsnStatus = createString("dscsnStatus");

    public QDscsnInfo(String variable) {
        this(DscsnInfo.class, forVariable(variable), INITS);
    }

    public QDscsnInfo(Path<? extends DscsnInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDscsnInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDscsnInfo(PathMetadata metadata, PathInits inits) {
        this(DscsnInfo.class, metadata, inits);
    }

    public QDscsnInfo(Class<? extends DscsnInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dscsnApply = inits.isInitialized("dscsnApply") ? new QDscsnApply(forProperty("dscsnApply"), inits.get("dscsnApply")) : null;
    }

}

