package com.secondprojinitiumback.common.login.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginInfo is a Querydsl query type for LoginInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoginInfo extends EntityPathBase<LoginInfo> {

    private static final long serialVersionUID = 1188292161L;

    public static final QLoginInfo loginInfo = new QLoginInfo("loginInfo");

    public final com.secondprojinitiumback.common.domain.base.QBaseEntity _super = new com.secondprojinitiumback.common.domain.base.QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> accountJoinDate = createDateTime("accountJoinDate", java.time.LocalDateTime.class);

    public final StringPath accountStatusCode = createString("accountStatusCode");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final StringPath createdIp = _super.createdIp;

    //inherited
    public final NumberPath<Long> createdProgramId = _super.createdProgramId;

    public final DateTimePath<java.time.LocalDateTime> lastLoginDateTime = createDateTime("lastLoginDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastPasswordChangeDateTime = createDateTime("lastPasswordChangeDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> loginFailCount = createNumber("loginFailCount", Integer.class);

    public final StringPath loginId = createString("loginId");

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath modifiedIp = _super.modifiedIp;

    //inherited
    public final NumberPath<Long> modifiedProgramId = _super.modifiedProgramId;

    public final StringPath password = createString("password");

    public final BooleanPath passwordChangeRequired = createBoolean("passwordChangeRequired");

    public final StringPath userType = createString("userType");

    public QLoginInfo(String variable) {
        super(LoginInfo.class, forVariable(variable));
    }

    public QLoginInfo(Path<? extends LoginInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginInfo(PathMetadata metadata) {
        super(LoginInfo.class, metadata);
    }

}

