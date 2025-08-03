package com.secondprojinitiumback.common.login.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLoginAuthInfo is a Querydsl query type for LoginAuthInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoginAuthInfo extends EntityPathBase<LoginAuthInfo> {

    private static final long serialVersionUID = -1662311799L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLoginAuthInfo loginAuthInfo = new QLoginAuthInfo("loginAuthInfo");

    public final StringPath accessToken = createString("accessToken");

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isForcedLogout = createBoolean("isForcedLogout");

    public final DateTimePath<java.time.LocalDateTime> issuedAt = createDateTime("issuedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastUsedAt = createDateTime("lastUsedAt", java.time.LocalDateTime.class);

    public final QLoginInfo loginInfo;

    public final StringPath refreshToken = createString("refreshToken");

    public QLoginAuthInfo(String variable) {
        this(LoginAuthInfo.class, forVariable(variable), INITS);
    }

    public QLoginAuthInfo(Path<? extends LoginAuthInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLoginAuthInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLoginAuthInfo(PathMetadata metadata, PathInits inits) {
        this(LoginAuthInfo.class, metadata, inits);
    }

    public QLoginAuthInfo(Class<? extends LoginAuthInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.loginInfo = inits.isInitialized("loginInfo") ? new QLoginInfo(forProperty("loginInfo")) : null;
    }

}

