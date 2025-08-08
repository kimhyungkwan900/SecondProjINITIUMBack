package com.secondprojinitiumback.admin.Mileage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMileageTotal is a Querydsl query type for MileageTotal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMileageTotal extends EntityPathBase<MileageTotal> {

    private static final long serialVersionUID = -1615538611L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMileageTotal mileageTotal = new QMileageTotal("mileageTotal");

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public final StringPath studentNo = createString("studentNo");

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public QMileageTotal(String variable) {
        this(MileageTotal.class, forVariable(variable), INITS);
    }

    public QMileageTotal(Path<? extends MileageTotal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMileageTotal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMileageTotal(PathMetadata metadata, PathInits inits) {
        this(MileageTotal.class, metadata, inits);
    }

    public QMileageTotal(Class<? extends MileageTotal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

