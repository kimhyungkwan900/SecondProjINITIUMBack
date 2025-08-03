package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosticScoreLevel is a Querydsl query type for DiagnosticScoreLevel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiagnosticScoreLevel extends EntityPathBase<DiagnosticScoreLevel> {

    private static final long serialVersionUID = 2105008875L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosticScoreLevel diagnosticScoreLevel = new QDiagnosticScoreLevel("diagnosticScoreLevel");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath levelName = createString("levelName");

    public final NumberPath<Integer> maxScore = createNumber("maxScore", Integer.class);

    public final NumberPath<Integer> minScore = createNumber("minScore", Integer.class);

    public final QDiagnosticTest test;

    public QDiagnosticScoreLevel(String variable) {
        this(DiagnosticScoreLevel.class, forVariable(variable), INITS);
    }

    public QDiagnosticScoreLevel(Path<? extends DiagnosticScoreLevel> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosticScoreLevel(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosticScoreLevel(PathMetadata metadata, PathInits inits) {
        this(DiagnosticScoreLevel.class, metadata, inits);
    }

    public QDiagnosticScoreLevel(Class<? extends DiagnosticScoreLevel> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.test = inits.isInitialized("test") ? new QDiagnosticTest(forProperty("test"), inits.get("test")) : null;
    }

}

