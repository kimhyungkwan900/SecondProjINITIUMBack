package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIdealTalentProfile is a Querydsl query type for IdealTalentProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdealTalentProfile extends EntityPathBase<IdealTalentProfile> {

    private static final long serialVersionUID = -159719181L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIdealTalentProfile idealTalentProfile = new QIdealTalentProfile("idealTalentProfile");

    public final QCoreCompetencyCategory coreCompetencyCategories;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QIdealTalentProfile(String variable) {
        this(IdealTalentProfile.class, forVariable(variable), INITS);
    }

    public QIdealTalentProfile(Path<? extends IdealTalentProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIdealTalentProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIdealTalentProfile(PathMetadata metadata, PathInits inits) {
        this(IdealTalentProfile.class, metadata, inits);
    }

    public QIdealTalentProfile(Class<? extends IdealTalentProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coreCompetencyCategories = inits.isInitialized("coreCompetencyCategories") ? new QCoreCompetencyCategory(forProperty("coreCompetencyCategories"), inits.get("coreCompetencyCategories")) : null;
    }

}

