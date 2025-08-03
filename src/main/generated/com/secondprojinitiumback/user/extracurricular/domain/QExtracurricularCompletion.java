package com.secondprojinitiumback.user.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularCompletion is a Querydsl query type for ExtracurricularCompletion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularCompletion extends EntityPathBase<ExtracurricularCompletion> {

    private static final long serialVersionUID = 2106561199L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularCompletion extracurricularCompletion = new QExtracurricularCompletion("extracurricularCompletion");

    public final DateTimePath<java.time.LocalDateTime> eduFnshDt = createDateTime("eduFnshDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> eduFnshId = createNumber("eduFnshId", Long.class);

    public final StringPath eduFnshYn = createString("eduFnshYn");

    public final QExtracurricularApply extracurricularApply;

    public QExtracurricularCompletion(String variable) {
        this(ExtracurricularCompletion.class, forVariable(variable), INITS);
    }

    public QExtracurricularCompletion(Path<? extends ExtracurricularCompletion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularCompletion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularCompletion(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularCompletion.class, metadata, inits);
    }

    public QExtracurricularCompletion(Class<? extends ExtracurricularCompletion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularApply = inits.isInitialized("extracurricularApply") ? new QExtracurricularApply(forProperty("extracurricularApply"), inits.get("extracurricularApply")) : null;
    }

}

