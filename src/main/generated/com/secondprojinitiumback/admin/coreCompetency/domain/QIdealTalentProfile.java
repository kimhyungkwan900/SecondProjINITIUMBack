package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIdealTalentProfile is a Querydsl query type for IdealTalentProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIdealTalentProfile extends EntityPathBase<IdealTalentProfile> {

    private static final long serialVersionUID = -159719181L;

    public static final QIdealTalentProfile idealTalentProfile = new QIdealTalentProfile("idealTalentProfile");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QIdealTalentProfile(String variable) {
        super(IdealTalentProfile.class, forVariable(variable));
    }

    public QIdealTalentProfile(Path<? extends IdealTalentProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIdealTalentProfile(PathMetadata metadata) {
        super(IdealTalentProfile.class, metadata);
    }

}

