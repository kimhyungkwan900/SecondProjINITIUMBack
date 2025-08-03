package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularAttendance is a Querydsl query type for ExtracurricularAttendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularAttendance extends EntityPathBase<ExtracurricularAttendance> {

    private static final long serialVersionUID = -2093804512L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularAttendance extracurricularAttendance = new QExtracurricularAttendance("extracurricularAttendance");

    public final DateTimePath<java.time.LocalDateTime> atndcDt = createDateTime("atndcDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> atndcId = createNumber("atndcId", Long.class);

    public final StringPath atndcYn = createString("atndcYn");

    public final QExtracurricularSchedule extracurricularSchedule;

    public final com.secondprojinitiumback.admin.extracurricular.domain.test.QStdntInfo stdntInfo;

    public QExtracurricularAttendance(String variable) {
        this(ExtracurricularAttendance.class, forVariable(variable), INITS);
    }

    public QExtracurricularAttendance(Path<? extends ExtracurricularAttendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularAttendance(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularAttendance.class, metadata, inits);
    }

    public QExtracurricularAttendance(Class<? extends ExtracurricularAttendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularSchedule = inits.isInitialized("extracurricularSchedule") ? new QExtracurricularSchedule(forProperty("extracurricularSchedule"), inits.get("extracurricularSchedule")) : null;
        this.stdntInfo = inits.isInitialized("stdntInfo") ? new com.secondprojinitiumback.admin.extracurricular.domain.test.QStdntInfo(forProperty("stdntInfo")) : null;
    }

}

