package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularSurvey is a Querydsl query type for ExtracurricularSurvey
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularSurvey extends EntityPathBase<ExtracurricularSurvey> {

    private static final long serialVersionUID = -826405871L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularSurvey extracurricularSurvey = new QExtracurricularSurvey("extracurricularSurvey");

    public final QExtracurricularProgram extracurricularProgram;

    public final StringPath srvyBgngDt = createString("srvyBgngDt");

    public final StringPath srvyEndDt = createString("srvyEndDt");

    public final NumberPath<Long> srvyId = createNumber("srvyId", Long.class);

    public final StringPath srvyQitemCn = createString("srvyQitemCn");

    public final StringPath srvyTtl = createString("srvyTtl");

    public QExtracurricularSurvey(String variable) {
        this(ExtracurricularSurvey.class, forVariable(variable), INITS);
    }

    public QExtracurricularSurvey(Path<? extends ExtracurricularSurvey> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularSurvey(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularSurvey(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularSurvey.class, metadata, inits);
    }

    public QExtracurricularSurvey(Class<? extends ExtracurricularSurvey> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularProgram = inits.isInitialized("extracurricularProgram") ? new QExtracurricularProgram(forProperty("extracurricularProgram"), inits.get("extracurricularProgram")) : null;
    }

}

