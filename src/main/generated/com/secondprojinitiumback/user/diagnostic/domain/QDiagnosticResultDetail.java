package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosticResultDetail is a Querydsl query type for DiagnosticResultDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiagnosticResultDetail extends EntityPathBase<DiagnosticResultDetail> {

    private static final long serialVersionUID = -1862378649L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosticResultDetail diagnosticResultDetail = new QDiagnosticResultDetail("diagnosticResultDetail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QDiagnosticQuestion question;

    public final QDiagnosticResult result;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> selectedValue = createNumber("selectedValue", Integer.class);

    public QDiagnosticResultDetail(String variable) {
        this(DiagnosticResultDetail.class, forVariable(variable), INITS);
    }

    public QDiagnosticResultDetail(Path<? extends DiagnosticResultDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosticResultDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosticResultDetail(PathMetadata metadata, PathInits inits) {
        this(DiagnosticResultDetail.class, metadata, inits);
    }

    public QDiagnosticResultDetail(Class<? extends DiagnosticResultDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QDiagnosticQuestion(forProperty("question"), inits.get("question")) : null;
        this.result = inits.isInitialized("result") ? new QDiagnosticResult(forProperty("result"), inits.get("result")) : null;
    }

}

