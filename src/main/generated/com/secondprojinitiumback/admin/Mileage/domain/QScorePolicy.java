package com.secondprojinitiumback.admin.Mileage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScorePolicy is a Querydsl query type for ScorePolicy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScorePolicy extends EntityPathBase<ScorePolicy> {

    private static final long serialVersionUID = 1059699281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScorePolicy scorePolicy = new QScorePolicy("scorePolicy");

    public final NumberPath<Long> attendanceId = createNumber("attendanceId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMileageItem mileageItem;

    public final com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram program;

    public final NumberPath<Integer> requiredAttendance = createNumber("requiredAttendance", Integer.class);

    public final StringPath scoreCriteria = createString("scoreCriteria");

    public final NumberPath<Double> scoreRate = createNumber("scoreRate", Double.class);

    public final StringPath useYn = createString("useYn");

    public QScorePolicy(String variable) {
        this(ScorePolicy.class, forVariable(variable), INITS);
    }

    public QScorePolicy(Path<? extends ScorePolicy> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScorePolicy(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScorePolicy(PathMetadata metadata, PathInits inits) {
        this(ScorePolicy.class, metadata, inits);
    }

    public QScorePolicy(Class<? extends ScorePolicy> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mileageItem = inits.isInitialized("mileageItem") ? new QMileageItem(forProperty("mileageItem"), inits.get("mileageItem")) : null;
        this.program = inits.isInitialized("program") ? new com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram(forProperty("program"), inits.get("program")) : null;
    }

}

