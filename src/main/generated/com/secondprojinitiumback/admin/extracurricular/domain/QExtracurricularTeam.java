package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularTeam is a Querydsl query type for ExtracurricularTeam
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularTeam extends EntityPathBase<ExtracurricularTeam> {

    private static final long serialVersionUID = 1308649684L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularTeam extracurricularTeam = new QExtracurricularTeam("extracurricularTeam");

    public final QExtracurricularProgram extracurricularProgram;

    public final DateTimePath<java.time.LocalDateTime> teamCrtYmd = createDateTime("teamCrtYmd", java.time.LocalDateTime.class);

    public final NumberPath<Long> teamId = createNumber("teamId", Long.class);

    public final NumberPath<Integer> teamNope = createNumber("teamNope", Integer.class);

    public QExtracurricularTeam(String variable) {
        this(ExtracurricularTeam.class, forVariable(variable), INITS);
    }

    public QExtracurricularTeam(Path<? extends ExtracurricularTeam> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularTeam(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularTeam(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularTeam.class, metadata, inits);
    }

    public QExtracurricularTeam(Class<? extends ExtracurricularTeam> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularProgram = inits.isInitialized("extracurricularProgram") ? new QExtracurricularProgram(forProperty("extracurricularProgram"), inits.get("extracurricularProgram")) : null;
    }

}

