package com.secondprojinitiumback.user.consult.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDscsnSchedule is a Querydsl query type for DscsnSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDscsnSchedule extends EntityPathBase<DscsnSchedule> {

    private static final long serialVersionUID = -276224755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDscsnSchedule dscsnSchedule = new QDscsnSchedule("dscsnSchedule");

    public final StringPath dscsnDtId = createString("dscsnDtId");

    public final StringPath dscsnYn = createString("dscsnYn");

    public final com.secondprojinitiumback.user.employee.domain.QEmployee employee;

    public final StringPath possibleDate = createString("possibleDate");

    public final StringPath possibleTime = createString("possibleTime");

    public QDscsnSchedule(String variable) {
        this(DscsnSchedule.class, forVariable(variable), INITS);
    }

    public QDscsnSchedule(Path<? extends DscsnSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDscsnSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDscsnSchedule(PathMetadata metadata, PathInits inits) {
        this(DscsnSchedule.class, metadata, inits);
    }

    public QDscsnSchedule(Class<? extends DscsnSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new com.secondprojinitiumback.user.employee.domain.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

