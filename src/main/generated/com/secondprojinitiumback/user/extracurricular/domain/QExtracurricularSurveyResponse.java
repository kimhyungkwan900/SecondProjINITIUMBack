package com.secondprojinitiumback.user.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularSurveyResponse is a Querydsl query type for ExtracurricularSurveyResponse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularSurveyResponse extends EntityPathBase<ExtracurricularSurveyResponse> {

    private static final long serialVersionUID = -657116402L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularSurveyResponse extracurricularSurveyResponse = new QExtracurricularSurveyResponse("extracurricularSurveyResponse");

    public final com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularSurvey extracurricularSurvey;

    public final NumberPath<Integer> srvyDgstfnScr = createNumber("srvyDgstfnScr", Integer.class);

    public final NumberPath<Long> srvyRspnsId = createNumber("srvyRspnsId", Long.class);

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public final StringPath surveyResponseContent = createString("surveyResponseContent");

    public final DateTimePath<java.time.LocalDateTime> surveyResponseDate = createDateTime("surveyResponseDate", java.time.LocalDateTime.class);

    public QExtracurricularSurveyResponse(String variable) {
        this(ExtracurricularSurveyResponse.class, forVariable(variable), INITS);
    }

    public QExtracurricularSurveyResponse(Path<? extends ExtracurricularSurveyResponse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularSurveyResponse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularSurveyResponse(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularSurveyResponse.class, metadata, inits);
    }

    public QExtracurricularSurveyResponse(Class<? extends ExtracurricularSurveyResponse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.extracurricularSurvey = inits.isInitialized("extracurricularSurvey") ? new com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularSurvey(forProperty("extracurricularSurvey"), inits.get("extracurricularSurvey")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

