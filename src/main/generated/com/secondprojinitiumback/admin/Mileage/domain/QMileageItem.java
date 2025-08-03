package com.secondprojinitiumback.admin.Mileage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMileageItem is a Querydsl query type for MileageItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMileageItem extends EntityPathBase<MileageItem> {

    private static final long serialVersionUID = -883721494L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMileageItem mileageItem = new QMileageItem("mileageItem");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemCode = createString("itemCode");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram program;

    public final ListPath<ScorePolicy, QScorePolicy> scorePolicies = this.<ScorePolicy, QScorePolicy>createList("scorePolicies", ScorePolicy.class, QScorePolicy.class, PathInits.DIRECT2);

    public QMileageItem(String variable) {
        this(MileageItem.class, forVariable(variable), INITS);
    }

    public QMileageItem(Path<? extends MileageItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMileageItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMileageItem(PathMetadata metadata, PathInits inits) {
        this(MileageItem.class, metadata, inits);
    }

    public QMileageItem(Class<? extends MileageItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.program = inits.isInitialized("program") ? new com.secondprojinitiumback.admin.extracurricular.domain.QExtracurricularProgram(forProperty("program"), inits.get("program")) : null;
    }

}

