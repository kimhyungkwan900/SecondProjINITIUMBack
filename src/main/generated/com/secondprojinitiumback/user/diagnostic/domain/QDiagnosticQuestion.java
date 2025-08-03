package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosticQuestion is a Querydsl query type for DiagnosticQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiagnosticQuestion extends EntityPathBase<DiagnosticQuestion> {

    private static final long serialVersionUID = 582174975L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosticQuestion diagnosticQuestion = new QDiagnosticQuestion("diagnosticQuestion");

    public final ListPath<DiagnosticAnswer, QDiagnosticAnswer> answers = this.<DiagnosticAnswer, QDiagnosticAnswer>createList("answers", DiagnosticAnswer.class, QDiagnosticAnswer.class, PathInits.DIRECT2);

    public final EnumPath<AnswerType> answerType = createEnum("answerType", AnswerType.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final QDiagnosticTest test;

    public QDiagnosticQuestion(String variable) {
        this(DiagnosticQuestion.class, forVariable(variable), INITS);
    }

    public QDiagnosticQuestion(Path<? extends DiagnosticQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosticQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosticQuestion(PathMetadata metadata, PathInits inits) {
        this(DiagnosticQuestion.class, metadata, inits);
    }

    public QDiagnosticQuestion(Class<? extends DiagnosticQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.test = inits.isInitialized("test") ? new QDiagnosticTest(forProperty("test"), inits.get("test")) : null;
    }

}

