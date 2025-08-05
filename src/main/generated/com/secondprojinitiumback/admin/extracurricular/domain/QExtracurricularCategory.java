package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularCategory is a Querydsl query type for ExtracurricularCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularCategory extends EntityPathBase<ExtracurricularCategory> {

    private static final long serialVersionUID = 2044121077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularCategory extracurricularCategory = new QExtracurricularCategory("extracurricularCategory");

    public final StringPath ctgryDtl = createString("ctgryDtl");

    public final NumberPath<Long> ctgryId = createNumber("ctgryId", Long.class);

    public final StringPath ctgryNm = createString("ctgryNm");

    public final StringPath ctgryUseYn = createString("ctgryUseYn");

    public final DateTimePath<java.time.LocalDateTime> dataCrtDt = createDateTime("dataCrtDt", java.time.LocalDateTime.class);

    public final com.secondprojinitiumback.common.domain.QSchoolSubject schoolSubject;

    public final NumberPath<Long> stgrId = createNumber("stgrId", Long.class);

    public QExtracurricularCategory(String variable) {
        this(ExtracurricularCategory.class, forVariable(variable), INITS);
    }

    public QExtracurricularCategory(Path<? extends ExtracurricularCategory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularCategory(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularCategory.class, metadata, inits);
    }

    public QExtracurricularCategory(Class<? extends ExtracurricularCategory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schoolSubject = inits.isInitialized("schoolSubject") ? new com.secondprojinitiumback.common.domain.QSchoolSubject(forProperty("schoolSubject"), inits.get("schoolSubject")) : null;
    }

}

