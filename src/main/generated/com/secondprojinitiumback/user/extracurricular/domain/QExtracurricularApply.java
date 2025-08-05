package com.secondprojinitiumback.user.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularApply is a Querydsl query type for ExtracurricularApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularApply extends EntityPathBase<ExtracurricularApply> {

    private static final long serialVersionUID = 489069755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularApply extracurricularApply = new QExtracurricularApply("extracurricularApply");

    public final EnumPath<com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm> aprySttsNm = createEnum("aprySttsNm", com.secondprojinitiumback.user.extracurricular.domain.enums.AprySttsNm.class);

    public final StringPath delYn = createString("delYn");

    public final StringPath eduAplyCn = createString("eduAplyCn");

    public final DateTimePath<java.time.LocalDateTime> eduAplyDt = createDateTime("eduAplyDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> eduAplyId = createNumber("eduAplyId", Long.class);

    public final com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram extracurricularProgram;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public QExtracurricularApply(String variable) {
        this(ExtracurricularApply.class, forVariable(variable), INITS);
    }

    public QExtracurricularApply(Path<? extends ExtracurricularApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularApply(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularApply.class, metadata, inits);
    }

    public QExtracurricularApply(Class<? extends ExtracurricularApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularProgram = inits.isInitialized("extracurricularProgram") ? new com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram(forProperty("extracurricularProgram"), inits.get("extracurricularProgram")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

