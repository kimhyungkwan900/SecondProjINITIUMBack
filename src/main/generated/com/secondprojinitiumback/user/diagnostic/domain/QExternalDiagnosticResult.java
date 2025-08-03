package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExternalDiagnosticResult is a Querydsl query type for ExternalDiagnosticResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExternalDiagnosticResult extends EntityPathBase<ExternalDiagnosticResult> {

    private static final long serialVersionUID = -63647L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExternalDiagnosticResult externalDiagnosticResult = new QExternalDiagnosticResult("externalDiagnosticResult");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath inspectCode = createString("inspectCode");

    public final StringPath resultUrl = createString("resultUrl");

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public final DateTimePath<java.time.LocalDateTime> submittedAt = createDateTime("submittedAt", java.time.LocalDateTime.class);

    public final QExternalDiagnosticTest test;

    public QExternalDiagnosticResult(String variable) {
        this(ExternalDiagnosticResult.class, forVariable(variable), INITS);
    }

    public QExternalDiagnosticResult(Path<? extends ExternalDiagnosticResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExternalDiagnosticResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExternalDiagnosticResult(PathMetadata metadata, PathInits inits) {
        this(ExternalDiagnosticResult.class, metadata, inits);
    }

    public QExternalDiagnosticResult(Class<? extends ExternalDiagnosticResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
        this.test = inits.isInitialized("test") ? new QExternalDiagnosticTest(forProperty("test"), inits.get("test")) : null;
    }

}

