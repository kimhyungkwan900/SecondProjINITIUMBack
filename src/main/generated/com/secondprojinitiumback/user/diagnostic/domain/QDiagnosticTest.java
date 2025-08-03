package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDiagnosticTest is a Querydsl query type for DiagnosticTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDiagnosticTest extends EntityPathBase<DiagnosticTest> {

    private static final long serialVersionUID = 1151583787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDiagnosticTest diagnosticTest = new QDiagnosticTest("diagnosticTest");

    public final com.secondprojinitiumback.common.domain.QCommonCode categoryCode;

    public final StringPath categoryGroup = createString("categoryGroup");

    public final StringPath categoryValue = createString("categoryValue");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<DiagnosticQuestion, QDiagnosticQuestion> questions = this.<DiagnosticQuestion, QDiagnosticQuestion>createList("questions", DiagnosticQuestion.class, QDiagnosticQuestion.class, PathInits.DIRECT2);

    public final ListPath<DiagnosticScoreLevel, QDiagnosticScoreLevel> scoreLevels = this.<DiagnosticScoreLevel, QDiagnosticScoreLevel>createList("scoreLevels", DiagnosticScoreLevel.class, QDiagnosticScoreLevel.class, PathInits.DIRECT2);

    public final StringPath useYn = createString("useYn");

    public QDiagnosticTest(String variable) {
        this(DiagnosticTest.class, forVariable(variable), INITS);
    }

    public QDiagnosticTest(Path<? extends DiagnosticTest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDiagnosticTest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDiagnosticTest(PathMetadata metadata, PathInits inits) {
        this(DiagnosticTest.class, metadata, inits);
    }

    public QDiagnosticTest(Class<? extends DiagnosticTest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryCode = inits.isInitialized("categoryCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("categoryCode"), inits.get("categoryCode")) : null;
    }

}

