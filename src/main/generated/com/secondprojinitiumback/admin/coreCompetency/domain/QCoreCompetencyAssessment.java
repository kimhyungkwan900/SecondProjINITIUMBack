package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoreCompetencyAssessment is a Querydsl query type for CoreCompetencyAssessment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoreCompetencyAssessment extends EntityPathBase<CoreCompetencyAssessment> {

    private static final long serialVersionUID = 204577961L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoreCompetencyAssessment coreCompetencyAssessment = new QCoreCompetencyAssessment("coreCompetencyAssessment");

    public final StringPath academicYear = createString("academicYear");

    public final StringPath assessmentName = createString("assessmentName");

    public final StringPath assessmentNo = createString("assessmentNo");

    public final StringPath endDate = createString("endDate");

    public final StringPath guideContent = createString("guideContent");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.secondprojinitiumback.common.domain.QCommonCode onlineExecCode;

    public final StringPath onlineExecGroupCode = createString("onlineExecGroupCode");

    public final StringPath registerDate = createString("registerDate");

    public final com.secondprojinitiumback.common.domain.QSchoolSubject schoolSubject;

    public final com.secondprojinitiumback.common.domain.QCommonCode semesterCode;

    public final StringPath semesterGroup = createString("semesterGroup");

    public final StringPath startDate = createString("startDate");

    public QCoreCompetencyAssessment(String variable) {
        this(CoreCompetencyAssessment.class, forVariable(variable), INITS);
    }

    public QCoreCompetencyAssessment(Path<? extends CoreCompetencyAssessment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoreCompetencyAssessment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoreCompetencyAssessment(PathMetadata metadata, PathInits inits) {
        this(CoreCompetencyAssessment.class, metadata, inits);
    }

    public QCoreCompetencyAssessment(Class<? extends CoreCompetencyAssessment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.onlineExecCode = inits.isInitialized("onlineExecCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("onlineExecCode"), inits.get("onlineExecCode")) : null;
        this.schoolSubject = inits.isInitialized("schoolSubject") ? new com.secondprojinitiumback.common.domain.QSchoolSubject(forProperty("schoolSubject"), inits.get("schoolSubject")) : null;
        this.semesterCode = inits.isInitialized("semesterCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("semesterCode"), inits.get("semesterCode")) : null;
    }

}

