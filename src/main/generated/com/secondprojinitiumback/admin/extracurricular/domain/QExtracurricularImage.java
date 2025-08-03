package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularImage is a Querydsl query type for ExtracurricularImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularImage extends EntityPathBase<ExtracurricularImage> {

    private static final long serialVersionUID = 1903514052L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularImage extracurricularImage = new QExtracurricularImage("extracurricularImage");

    public final QExtracurricularProgram extracurricularProgram;

    public final StringPath imgFilePathNm = createString("imgFilePathNm");

    public final NumberPath<Integer> imgFileSz = createNumber("imgFileSz", Integer.class);

    public final NumberPath<Long> imgId = createNumber("imgId", Long.class);

    public final EnumPath<com.secondprojinitiumback.admin.extracurricular.domain.enums.ImgType> imgType = createEnum("imgType", com.secondprojinitiumback.admin.extracurricular.domain.enums.ImgType.class);

    public QExtracurricularImage(String variable) {
        this(ExtracurricularImage.class, forVariable(variable), INITS);
    }

    public QExtracurricularImage(Path<? extends ExtracurricularImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularImage(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularImage.class, metadata, inits);
    }

    public QExtracurricularImage(Class<? extends ExtracurricularImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularProgram = inits.isInitialized("extracurricularProgram") ? new QExtracurricularProgram(forProperty("extracurricularProgram"), inits.get("extracurricularProgram")) : null;
    }

}

