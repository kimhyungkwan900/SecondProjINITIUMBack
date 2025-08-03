package com.secondprojinitiumback.common.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCommonCodeId is a Querydsl query type for CommonCodeId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCommonCodeId extends BeanPath<CommonCodeId> {

    private static final long serialVersionUID = -552714834L;

    public static final QCommonCodeId commonCodeId = new QCommonCodeId("commonCodeId");

    public final StringPath code = createString("code");

    public final StringPath codeGroup = createString("codeGroup");

    public QCommonCodeId(String variable) {
        super(CommonCodeId.class, forVariable(variable));
    }

    public QCommonCodeId(Path<? extends CommonCodeId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCommonCodeId(PathMetadata metadata) {
        super(CommonCodeId.class, metadata);
    }

}

