package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubCompetencyCategory is a Querydsl query type for SubCompetencyCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubCompetencyCategory extends EntityPathBase<SubCompetencyCategory> {

    private static final long serialVersionUID = 952562256L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSubCompetencyCategory subCompetencyCategory = new QSubCompetencyCategory("subCompetencyCategory");

    public final ListPath<BehaviorIndicator, QBehaviorIndicator> behaviorIndicators = this.<BehaviorIndicator, QBehaviorIndicator>createList("behaviorIndicators", BehaviorIndicator.class, QBehaviorIndicator.class, PathInits.DIRECT2);

    public final com.secondprojinitiumback.common.domain.QCommonCode competencyCategory;

    public final StringPath competencyCategoryGroup = createString("competencyCategoryGroup");

    public final QCoreCompetencyCategory coreCompetencyCategory;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath subCategoryName = createString("subCategoryName");

    public final StringPath subCategoryNote = createString("subCategoryNote");

    public QSubCompetencyCategory(String variable) {
        this(SubCompetencyCategory.class, forVariable(variable), INITS);
    }

    public QSubCompetencyCategory(Path<? extends SubCompetencyCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSubCompetencyCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSubCompetencyCategory(PathMetadata metadata, PathInits inits) {
        this(SubCompetencyCategory.class, metadata, inits);
    }

    public QSubCompetencyCategory(Class<? extends SubCompetencyCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.competencyCategory = inits.isInitialized("competencyCategory") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("competencyCategory"), inits.get("competencyCategory")) : null;
        this.coreCompetencyCategory = inits.isInitialized("coreCompetencyCategory") ? new QCoreCompetencyCategory(forProperty("coreCompetencyCategory"), inits.get("coreCompetencyCategory")) : null;
    }

}

