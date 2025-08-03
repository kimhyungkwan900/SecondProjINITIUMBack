package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoreCompetencyCategory is a Querydsl query type for CoreCompetencyCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoreCompetencyCategory extends EntityPathBase<CoreCompetencyCategory> {

    private static final long serialVersionUID = 759132325L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoreCompetencyCategory coreCompetencyCategory = new QCoreCompetencyCategory("coreCompetencyCategory");

    public final StringPath coreCategoryName = createString("coreCategoryName");

    public final StringPath coreCategoryNote = createString("coreCategoryNote");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QIdealTalentProfile idealTalentProfile;

    public QCoreCompetencyCategory(String variable) {
        this(CoreCompetencyCategory.class, forVariable(variable), INITS);
    }

    public QCoreCompetencyCategory(Path<? extends CoreCompetencyCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoreCompetencyCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoreCompetencyCategory(PathMetadata metadata, PathInits inits) {
        this(CoreCompetencyCategory.class, metadata, inits);
    }

    public QCoreCompetencyCategory(Class<? extends CoreCompetencyCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.idealTalentProfile = inits.isInitialized("idealTalentProfile") ? new QIdealTalentProfile(forProperty("idealTalentProfile")) : null;
    }

}

