package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularSchedule is a Querydsl query type for ExtracurricularSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularSchedule extends EntityPathBase<ExtracurricularSchedule> {

    private static final long serialVersionUID = 1295689102L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularSchedule extracurricularSchedule = new QExtracurricularSchedule("extracurricularSchedule");

    public final DateTimePath<java.time.LocalDateTime> eduDt = createDateTime("eduDt", java.time.LocalDateTime.class);

    public final TimePath<java.time.LocalTime> eduEdnTm = createTime("eduEdnTm", java.time.LocalTime.class);

    public final NumberPath<Long> eduShdlId = createNumber("eduShdlId", Long.class);

    public final QExtracurricularProgram extracurricularProgram;

    public QExtracurricularSchedule(String variable) {
        this(ExtracurricularSchedule.class, forVariable(variable), INITS);
    }

    public QExtracurricularSchedule(Path<? extends ExtracurricularSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularSchedule(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularSchedule.class, metadata, inits);
    }

    public QExtracurricularSchedule(Class<? extends ExtracurricularSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularProgram = inits.isInitialized("extracurricularProgram") ? new QExtracurricularProgram(forProperty("extracurricularProgram"), inits.get("extracurricularProgram")) : null;
    }

}

