package com.secondprojinitiumback.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUniversity is a Querydsl query type for University
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUniversity extends EntityPathBase<University> {

    private static final long serialVersionUID = -2103623351L;

    public static final QUniversity university = new QUniversity("university");

    public final com.secondprojinitiumback.common.domain.base.QBaseEntity _super = new com.secondprojinitiumback.common.domain.base.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath createdIp = _super.createdIp;

    //inherited
    public final NumberPath<Long> createdProgramId = _super.createdProgramId;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedIp = _super.modifiedIp;

    //inherited
    public final NumberPath<Long> modifiedProgramId = _super.modifiedProgramId;

    public final StringPath universityCode = createString("universityCode");

    public final StringPath universityName = createString("universityName");

    public QUniversity(String variable) {
        super(University.class, forVariable(variable));
    }

    public QUniversity(Path<? extends University> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUniversity(PathMetadata metadata) {
        super(University.class, metadata);
    }

}

