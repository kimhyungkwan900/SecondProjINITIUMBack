package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBehaviorIndicator is a Querydsl query type for BehaviorIndicator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBehaviorIndicator extends EntityPathBase<BehaviorIndicator> {

    private static final long serialVersionUID = 1759110738L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBehaviorIndicator behaviorIndicator = new QBehaviorIndicator("behaviorIndicator");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isCommon = createString("isCommon");

    public final com.secondprojinitiumback.common.domain.QCommonCode isCommonCode;

    public final StringPath isCommonGroupCode = createString("isCommonGroupCode");

    public final StringPath name = createString("name");

    public final com.secondprojinitiumback.common.domain.QSchoolSubject schoolSubject;

    public final QSubCompetencyCategory subCompetencyCategory;

    public QBehaviorIndicator(String variable) {
        this(BehaviorIndicator.class, forVariable(variable), INITS);
    }

    public QBehaviorIndicator(Path<? extends BehaviorIndicator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBehaviorIndicator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBehaviorIndicator(PathMetadata metadata, PathInits inits) {
        this(BehaviorIndicator.class, metadata, inits);
    }

    public QBehaviorIndicator(Class<? extends BehaviorIndicator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.isCommonCode = inits.isInitialized("isCommonCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("isCommonCode"), inits.get("isCommonCode")) : null;
        this.schoolSubject = inits.isInitialized("schoolSubject") ? new com.secondprojinitiumback.common.domain.QSchoolSubject(forProperty("schoolSubject"), inits.get("schoolSubject")) : null;
        this.subCompetencyCategory = inits.isInitialized("subCompetencyCategory") ? new QSubCompetencyCategory(forProperty("subCompetencyCategory"), inits.get("subCompetencyCategory")) : null;
    }

}

