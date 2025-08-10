package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudentCompetencyScore is a Querydsl query type for StudentCompetencyScore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudentCompetencyScore extends EntityPathBase<StudentCompetencyScore> {

    private static final long serialVersionUID = -716131419L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudentCompetencyScore studentCompetencyScore = new QStudentCompetencyScore("studentCompetencyScore");

    public final StringPath classificationCode = createString("classificationCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCoreCompetencyResult result;

    public final NumberPath<java.math.BigDecimal> standardScore = createNumber("standardScore", java.math.BigDecimal.class);

    public final QSubCompetencyCategory subCategory;

    public final NumberPath<java.math.BigDecimal> totalAverageScore = createNumber("totalAverageScore", java.math.BigDecimal.class);

    public QStudentCompetencyScore(String variable) {
        this(StudentCompetencyScore.class, forVariable(variable), INITS);
    }

    public QStudentCompetencyScore(Path<? extends StudentCompetencyScore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudentCompetencyScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudentCompetencyScore(PathMetadata metadata, PathInits inits) {
        this(StudentCompetencyScore.class, metadata, inits);
    }

    public QStudentCompetencyScore(Class<? extends StudentCompetencyScore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.result = inits.isInitialized("result") ? new QCoreCompetencyResult(forProperty("result"), inits.get("result")) : null;
        this.subCategory = inits.isInitialized("subCategory") ? new QSubCompetencyCategory(forProperty("subCategory"), inits.get("subCategory")) : null;
    }

}

