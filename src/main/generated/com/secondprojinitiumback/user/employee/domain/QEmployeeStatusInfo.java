package com.secondprojinitiumback.user.employee.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEmployeeStatusInfo is a Querydsl query type for EmployeeStatusInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployeeStatusInfo extends EntityPathBase<EmployeeStatusInfo> {

    private static final long serialVersionUID = 102141287L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEmployeeStatusInfo employeeStatusInfo = new QEmployeeStatusInfo("employeeStatusInfo");

    public final com.secondprojinitiumback.common.domain.QCommonCode employeeStatusGroup;

    public final StringPath employeeStatusName = createString("employeeStatusName");

    public final QEmployeeStatusId id;

    public QEmployeeStatusInfo(String variable) {
        this(EmployeeStatusInfo.class, forVariable(variable), INITS);
    }

    public QEmployeeStatusInfo(Path<? extends EmployeeStatusInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEmployeeStatusInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEmployeeStatusInfo(PathMetadata metadata, PathInits inits) {
        this(EmployeeStatusInfo.class, metadata, inits);
    }

    public QEmployeeStatusInfo(Class<? extends EmployeeStatusInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employeeStatusGroup = inits.isInitialized("employeeStatusGroup") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("employeeStatusGroup"), inits.get("employeeStatusGroup")) : null;
        this.id = inits.isInitialized("id") ? new QEmployeeStatusId(forProperty("id")) : null;
    }

}

