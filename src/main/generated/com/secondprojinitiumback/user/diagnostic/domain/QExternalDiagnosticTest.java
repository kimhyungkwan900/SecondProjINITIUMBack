package com.secondprojinitiumback.user.diagnostic.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExternalDiagnosticTest is a Querydsl query type for ExternalDiagnosticTest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExternalDiagnosticTest extends EntityPathBase<ExternalDiagnosticTest> {

    private static final long serialVersionUID = -420051754L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExternalDiagnosticTest externalDiagnosticTest = new QExternalDiagnosticTest("externalDiagnosticTest");

    public final com.secondprojinitiumback.common.domain.QCommonCode categoryCode;

    public final StringPath categoryGroup = createString("categoryGroup");

    public final StringPath categoryValue = createString("categoryValue");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath provider = createString("provider");

    public final StringPath questionApiCode = createString("questionApiCode");

    public final StringPath targetCode = createString("targetCode");

    public QExternalDiagnosticTest(String variable) {
        this(ExternalDiagnosticTest.class, forVariable(variable), INITS);
    }

    public QExternalDiagnosticTest(Path<? extends ExternalDiagnosticTest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExternalDiagnosticTest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExternalDiagnosticTest(PathMetadata metadata, PathInits inits) {
        this(ExternalDiagnosticTest.class, metadata, inits);
    }

    public QExternalDiagnosticTest(Class<? extends ExternalDiagnosticTest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryCode = inits.isInitialized("categoryCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("categoryCode"), inits.get("categoryCode")) : null;
    }

}

