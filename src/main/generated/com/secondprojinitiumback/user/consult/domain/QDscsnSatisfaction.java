package com.secondprojinitiumback.user.consult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDscsnSatisfaction is a Querydsl query type for DscsnSatisfaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDscsnSatisfaction extends EntityPathBase<DscsnSatisfaction> {

    private static final long serialVersionUID = -1847035902L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDscsnSatisfaction dscsnSatisfaction = new QDscsnSatisfaction("dscsnSatisfaction");

    public final StringPath dscsnImp = createString("dscsnImp");

    public final QDscsnInfo dscsnInfo;

    public final StringPath dscsnSatisfyId = createString("dscsnSatisfyId");

    public final StringPath dscsnSatisfyScore = createString("dscsnSatisfyScore");

    public QDscsnSatisfaction(String variable) {
        this(DscsnSatisfaction.class, forVariable(variable), INITS);
    }

    public QDscsnSatisfaction(Path<? extends DscsnSatisfaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDscsnSatisfaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDscsnSatisfaction(PathMetadata metadata, PathInits inits) {
        this(DscsnSatisfaction.class, metadata, inits);
    }

    public QDscsnSatisfaction(Class<? extends DscsnSatisfaction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dscsnInfo = inits.isInitialized("dscsnInfo") ? new QDscsnInfo(forProperty("dscsnInfo"), inits.get("dscsnInfo")) : null;
    }

}

