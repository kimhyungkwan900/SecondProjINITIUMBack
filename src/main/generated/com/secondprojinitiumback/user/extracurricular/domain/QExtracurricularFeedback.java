package com.secondprojinitiumback.user.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularFeedback is a Querydsl query type for ExtracurricularFeedback
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularFeedback extends EntityPathBase<ExtracurricularFeedback> {

    private static final long serialVersionUID = -23383272L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularFeedback extracurricularFeedback = new QExtracurricularFeedback("extracurricularFeedback");

    public final StringPath eduFeedbackCn = createString("eduFeedbackCn");

    public final StringPath eduFeedbackTtl = createString("eduFeedbackTtl");

    public final com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram extracurricularProgram;

    public final NumberPath<Long> feedbackId = createNumber("feedbackId", Long.class);

    public final StringPath infoRlsYn = createString("infoRlsYn");

    public final com.secondprojinitiumback.admin.extracurricular.domain.test.QStdntInfo stdntInfo;

    public final DateTimePath<java.time.LocalDateTime> updateDt = createDateTime("updateDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> wrtDt = createDateTime("wrtDt", java.time.LocalDateTime.class);

    public QExtracurricularFeedback(String variable) {
        this(ExtracurricularFeedback.class, forVariable(variable), INITS);
    }

    public QExtracurricularFeedback(Path<? extends ExtracurricularFeedback> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularFeedback(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularFeedback(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularFeedback.class, metadata, inits);
    }

    public QExtracurricularFeedback(Class<? extends ExtracurricularFeedback> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularProgram = inits.isInitialized("extracurricularProgram") ? new com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram(forProperty("extracurricularProgram"), inits.get("extracurricularProgram")) : null;
        this.stdntInfo = inits.isInitialized("stdntInfo") ? new com.secondprojinitiumback.admin.extracurricular.domain.test.QStdntInfo(forProperty("stdntInfo")) : null;
    }

}

