package com.secondprojinitiumback.common.security.domain;

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

    private static final long serialVersionUID = -1981794804L;

    public static final QLoginInfo loginInfo = new QLoginInfo("loginInfo");

    public final DateTimePath<java.time.LocalDateTime> accountJoinDate = createDateTime("accountJoinDate", java.time.LocalDateTime.class);

    public final StringPath accountStatusCode = createString("accountStatusCode");

    public final DateTimePath<java.time.LocalDateTime> lastLoginDateTime = createDateTime("lastLoginDateTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastPasswordChangeDateTime = createDateTime("lastPasswordChangeDateTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> loginFailCount = createNumber("loginFailCount", Integer.class);

    public final StringPath loginId = createString("loginId");

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

