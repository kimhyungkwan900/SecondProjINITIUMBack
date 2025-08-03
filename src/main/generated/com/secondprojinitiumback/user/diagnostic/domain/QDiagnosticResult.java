package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosticResult is a Querydsl query type for DiagnosticResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiagnosticResult extends EntityPathBase<DiagnosticResult> {

    private static final long serialVersionUID = -1486796938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosticResult diagnosticResult = new QDiagnosticResult("diagnosticResult");

    public final DateTimePath<java.time.LocalDateTime> completionDate = createDateTime("completionDate", java.time.LocalDateTime.class);

    public final ListPath<DiagnosticResultDetail, QDiagnosticResultDetail> details = this.<DiagnosticResultDetail, QDiagnosticResultDetail>createList("details", DiagnosticResultDetail.class, QDiagnosticResultDetail.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public final QDiagnosticTest test;

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public QDiagnosticResult(String variable) {
        this(DiagnosticResult.class, forVariable(variable), INITS);
    }

    public QDiagnosticResult(Path<? extends DiagnosticResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosticResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosticResult(PathMetadata metadata, PathInits inits) {
        this(DiagnosticResult.class, metadata, inits);
    }

    public QDiagnosticResult(Class<? extends DiagnosticResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
        this.test = inits.isInitialized("test") ? new QDiagnosticTest(forProperty("test"), inits.get("test")) : null;
    }

}

