package com.secondprojinitiumback.user.consult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDscsnKind is a Querydsl query type for DscsnKind
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDscsnKind extends EntityPathBase<DscsnKind> {

    private static final long serialVersionUID = -868452086L;

    public static final QDscsnKind dscsnKind = new QDscsnKind("dscsnKind");

    public final StringPath dscsnKindId = createString("dscsnKindId");

    public final StringPath dscsnKindName = createString("dscsnKindName");

    public final StringPath dscsnTypeName = createString("dscsnTypeName");

    public QDscsnKind(String variable) {
        super(DscsnKind.class, forVariable(variable));
    }

    public QDscsnKind(Path<? extends DscsnKind> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDscsnKind(PathMetadata metadata) {
        super(DscsnKind.class, metadata);
    }

}

