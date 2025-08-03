package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosticAnswer is a Querydsl query type for DiagnosticAnswer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiagnosticAnswer extends EntityPathBase<DiagnosticAnswer> {

    private static final long serialVersionUID = -1965179113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosticAnswer diagnosticAnswer = new QDiagnosticAnswer("diagnosticAnswer");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QDiagnosticQuestion question;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> selectValue = createNumber("selectValue", Integer.class);

    public QDiagnosticAnswer(String variable) {
        this(DiagnosticAnswer.class, forVariable(variable), INITS);
    }

    public QDiagnosticAnswer(Path<? extends DiagnosticAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosticAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosticAnswer(PathMetadata metadata, PathInits inits) {
        this(DiagnosticAnswer.class, metadata, inits);
    }

    public QDiagnosticAnswer(Class<? extends DiagnosticAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QDiagnosticQuestion(forProperty("question"), inits.get("question")) : null;
    }

}

