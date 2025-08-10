package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoreCompetencyResponse is a Querydsl query type for CoreCompetencyResponse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoreCompetencyResponse extends EntityPathBase<CoreCompetencyResponse> {

    private static final long serialVersionUID = 368297960L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoreCompetencyResponse coreCompetencyResponse = new QCoreCompetencyResponse("coreCompetencyResponse");

    public final StringPath completeDate = createString("completeDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCoreCompetencyQuestion question;

    public final NumberPath<Integer> resultScore = createNumber("resultScore", Integer.class);

    public final NumberPath<Integer> selectCount = createNumber("selectCount", Integer.class);

    public final QResponseChoiceOption selectedOption;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public QCoreCompetencyResponse(String variable) {
        this(CoreCompetencyResponse.class, forVariable(variable), INITS);
    }

    public QCoreCompetencyResponse(Path<? extends CoreCompetencyResponse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoreCompetencyResponse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoreCompetencyResponse(PathMetadata metadata, PathInits inits) {
        this(CoreCompetencyResponse.class, metadata, inits);
    }

    public QCoreCompetencyResponse(Class<? extends CoreCompetencyResponse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QCoreCompetencyQuestion(forProperty("question"), inits.get("question")) : null;
        this.selectedOption = inits.isInitialized("selectedOption") ? new QResponseChoiceOption(forProperty("selectedOption"), inits.get("selectedOption")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

