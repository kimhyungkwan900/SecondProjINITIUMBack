package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecommendedProgram is a Querydsl query type for RecommendedProgram
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendedProgram extends EntityPathBase<RecommendedProgram> {

    private static final long serialVersionUID = 597138388L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecommendedProgram recommendedProgram = new QRecommendedProgram("recommendedProgram");

    public final StringPath id = createString("id");

    public final QIdealTalentProfile idealTalentProfile;

    public final com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram program;

    public final StringPath reasonContent = createString("reasonContent");

    public final QCoreCompetencyResult result;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public QRecommendedProgram(String variable) {
        this(RecommendedProgram.class, forVariable(variable), INITS);
    }

    public QRecommendedProgram(Path<? extends RecommendedProgram> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecommendedProgram(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecommendedProgram(PathMetadata metadata, PathInits inits) {
        this(RecommendedProgram.class, metadata, inits);
    }

    public QRecommendedProgram(Class<? extends RecommendedProgram> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idealTalentProfile = inits.isInitialized("idealTalentProfile") ? new QIdealTalentProfile(forProperty("idealTalentProfile")) : null;
        this.program = inits.isInitialized("program") ? new com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram(forProperty("program"), inits.get("program")) : null;
        this.result = inits.isInitialized("result") ? new QCoreCompetencyResult(forProperty("result"), inits.get("result")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

