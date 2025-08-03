package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBehaviorIndicatorMajorQuestionMapping is a Querydsl query type for BehaviorIndicatorMajorQuestionMapping
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBehaviorIndicatorMajorQuestionMapping extends EntityPathBase<BehaviorIndicatorMajorQuestionMapping> {

    private static final long serialVersionUID = -993731455L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBehaviorIndicatorMajorQuestionMapping behaviorIndicatorMajorQuestionMapping = new QBehaviorIndicatorMajorQuestionMapping("behaviorIndicatorMajorQuestionMapping");

    public final QBehaviorIndicator behaviorIndicator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QCoreCompetencyQuestion question;

    public final com.secondprojinitiumback.common.domain.QSchoolSubject schoolSubject;

    public QBehaviorIndicatorMajorQuestionMapping(String variable) {
        this(BehaviorIndicatorMajorQuestionMapping.class, forVariable(variable), INITS);
    }

    public QBehaviorIndicatorMajorQuestionMapping(Path<? extends BehaviorIndicatorMajorQuestionMapping> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBehaviorIndicatorMajorQuestionMapping(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBehaviorIndicatorMajorQuestionMapping(PathMetadata metadata, PathInits inits) {
        this(BehaviorIndicatorMajorQuestionMapping.class, metadata, inits);
    }

    public QBehaviorIndicatorMajorQuestionMapping(Class<? extends BehaviorIndicatorMajorQuestionMapping> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.behaviorIndicator = inits.isInitialized("behaviorIndicator") ? new QBehaviorIndicator(forProperty("behaviorIndicator"), inits.get("behaviorIndicator")) : null;
        this.question = inits.isInitialized("question") ? new QCoreCompetencyQuestion(forProperty("question"), inits.get("question")) : null;
        this.schoolSubject = inits.isInitialized("schoolSubject") ? new com.secondprojinitiumback.common.domain.QSchoolSubject(forProperty("schoolSubject"), inits.get("schoolSubject")) : null;
    }

}

