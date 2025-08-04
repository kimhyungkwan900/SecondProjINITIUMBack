package com.secondprojinitiumback.admin.Mileage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMileagePerf is a Querydsl query type for MileagePerf
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMileagePerf extends EntityPathBase<MileagePerf> {

    private static final long serialVersionUID = -883526976L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMileagePerf mileagePerf = new QMileagePerf("mileagePerf");

    public final NumberPath<Integer> accMlg = createNumber("accMlg", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> canceledAt = createDateTime("canceledAt", java.time.LocalDateTime.class);

    public final StringPath cancelReason = createString("cancelReason");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMileageItem mileageItem;

    public final QScorePolicy scorePolicy;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public QMileagePerf(String variable) {
        this(MileagePerf.class, forVariable(variable), INITS);
    }

    public QMileagePerf(Path<? extends MileagePerf> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMileagePerf(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMileagePerf(PathMetadata metadata, PathInits inits) {
        this(MileagePerf.class, metadata, inits);
    }

    public QMileagePerf(Class<? extends MileagePerf> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mileageItem = inits.isInitialized("mileageItem") ? new QMileageItem(forProperty("mileageItem"), inits.get("mileageItem")) : null;
        this.scorePolicy = inits.isInitialized("scorePolicy") ? new QScorePolicy(forProperty("scorePolicy"), inits.get("scorePolicy")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

