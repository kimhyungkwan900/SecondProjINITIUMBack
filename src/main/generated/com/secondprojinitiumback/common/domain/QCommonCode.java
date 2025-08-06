package com.secondprojinitiumback.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommonCode is a Querydsl query type for CommonCode
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommonCode extends EntityPathBase<CommonCode> {

    private static final long serialVersionUID = -1108953805L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommonCode commonCode = new QCommonCode("commonCode");

    public final StringPath codeName = createString("codeName");

    public final QCommonCodeId id;

    public final StringPath remark = createString("remark");

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final StringPath useYn = createString("useYn");

    public QCommonCode(String variable) {
        this(CommonCode.class, forVariable(variable), INITS);
    }

    public QCommonCode(Path<? extends CommonCode> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommonCode(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommonCode(PathMetadata metadata, PathInits inits) {
        this(CommonCode.class, metadata, inits);
    }

    public QCommonCode(Class<? extends CommonCode> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCommonCodeId(forProperty("id")) : null;
    }

}

