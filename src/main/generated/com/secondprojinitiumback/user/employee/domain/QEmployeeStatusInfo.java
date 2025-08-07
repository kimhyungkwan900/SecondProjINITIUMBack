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

    public final com.secondprojinitiumback.common.domain.base.QBaseEntity _super = new com.secondprojinitiumback.common.domain.base.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath createdIp = _super.createdIp;

    //inherited
    public final NumberPath<Long> createdProgramId = _super.createdProgramId;

    public final com.secondprojinitiumback.common.domain.QCommonCode employeeStatusGroup;

    public final StringPath employeeStatusName = createString("employeeStatusName");

    public final QEmployeeStatusId id;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedIp = _super.modifiedIp;

    //inherited
    public final NumberPath<Long> modifiedProgramId = _super.modifiedProgramId;

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

