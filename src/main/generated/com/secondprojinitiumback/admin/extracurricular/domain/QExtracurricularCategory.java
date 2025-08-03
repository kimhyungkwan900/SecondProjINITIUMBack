package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExtracurricularCategory is a Querydsl query type for ExtracurricularCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularCategory extends EntityPathBase<ExtracurricularCategory> {

    private static final long serialVersionUID = 2044121077L;

    public static final QExtracurricularCategory extracurricularCategory = new QExtracurricularCategory("extracurricularCategory");

    public final NumberPath<Long> ctgryId = createNumber("ctgryId", Long.class);

    public final StringPath ctgryNm = createString("ctgryNm");

    public final StringPath ctgryUseYn = createString("ctgryUseYn");

    public final DateTimePath<java.time.LocalDateTime> dataCrtDt = createDateTime("dataCrtDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> stgrId = createNumber("stgrId", Long.class);

    public QExtracurricularCategory(String variable) {
        super(ExtracurricularCategory.class, forVariable(variable));
    }

    public QExtracurricularCategory(Path<? extends ExtracurricularCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExtracurricularCategory(PathMetadata metadata) {
        super(ExtracurricularCategory.class, metadata);
    }

}

