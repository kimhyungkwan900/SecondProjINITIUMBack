package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoreCompetencyResult is a Querydsl query type for CoreCompetencyResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoreCompetencyResult extends EntityPathBase<CoreCompetencyResult> {

    private static final long serialVersionUID = -106874492L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoreCompetencyResult coreCompetencyResult = new QCoreCompetencyResult("coreCompetencyResult");

    public final QCoreCompetencyAssessment assessment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCoreCompetencyResponse response;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public QCoreCompetencyResult(String variable) {
        this(CoreCompetencyResult.class, forVariable(variable), INITS);
    }

    public QCoreCompetencyResult(Path<? extends CoreCompetencyResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoreCompetencyResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoreCompetencyResult(PathMetadata metadata, PathInits inits) {
        this(CoreCompetencyResult.class, metadata, inits);
    }

    public QCoreCompetencyResult(Class<? extends CoreCompetencyResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.assessment = inits.isInitialized("assessment") ? new QCoreCompetencyAssessment(forProperty("assessment"), inits.get("assessment")) : null;
        this.response = inits.isInitialized("response") ? new QCoreCompetencyResponse(forProperty("response"), inits.get("response")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

