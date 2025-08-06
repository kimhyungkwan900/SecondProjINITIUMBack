package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompetencyImage is a Querydsl query type for CompetencyImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompetencyImage extends EntityPathBase<CompetencyImage> {

    private static final long serialVersionUID = -2014061773L;

    public static final QCompetencyImage competencyImage = new QCompetencyImage("competencyImage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgName = createString("imgName");

    public QCompetencyImage(String variable) {
        super(CompetencyImage.class, forVariable(variable));
    }

    public QCompetencyImage(Path<? extends CompetencyImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompetencyImage(PathMetadata metadata) {
        super(CompetencyImage.class, metadata);
    }

}

