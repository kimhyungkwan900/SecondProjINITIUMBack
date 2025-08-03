package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularTeamMember is a Querydsl query type for ExtracurricularTeamMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularTeamMember extends EntityPathBase<ExtracurricularTeamMember> {

    private static final long serialVersionUID = -1377636210L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularTeamMember extracurricularTeamMember = new QExtracurricularTeamMember("extracurricularTeamMember");

    public final QExtracurricularTeam extracurricularTeam;

    public final com.secondprojinitiumback.admin.extracurricular.domain.test.QStdntInfo stdntInfo;

    public final NumberPath<Long> teamMngId = createNumber("teamMngId", Long.class);

    public final StringPath teamQlfcYn = createString("teamQlfcYn");

    public QExtracurricularTeamMember(String variable) {
        this(ExtracurricularTeamMember.class, forVariable(variable), INITS);
    }

    public QExtracurricularTeamMember(Path<? extends ExtracurricularTeamMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularTeamMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularTeamMember(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularTeamMember.class, metadata, inits);
    }

    public QExtracurricularTeamMember(Class<? extends ExtracurricularTeamMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularTeam = inits.isInitialized("extracurricularTeam") ? new QExtracurricularTeam(forProperty("extracurricularTeam"), inits.get("extracurricularTeam")) : null;
        this.stdntInfo = inits.isInitialized("stdntInfo") ? new com.secondprojinitiumback.admin.extracurricular.domain.test.QStdntInfo(forProperty("stdntInfo")) : null;
    }

}

