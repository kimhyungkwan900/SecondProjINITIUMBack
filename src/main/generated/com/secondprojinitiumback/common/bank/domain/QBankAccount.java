package com.secondprojinitiumback.common.bank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBankAccount is a Querydsl query type for BankAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBankAccount extends EntityPathBase<BankAccount> {

    private static final long serialVersionUID = 819020138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBankAccount bankAccount = new QBankAccount("bankAccount");

    public final StringPath accountNo = createString("accountNo");

    public final StringPath accountType = createString("accountType");

    public final com.secondprojinitiumback.common.domain.QCommonCode bankCode;

    public final StringPath ownerId = createString("ownerId");

    public final StringPath useYn = createString("useYn");

    public QBankAccount(String variable) {
        this(BankAccount.class, forVariable(variable), INITS);
    }

    public QBankAccount(Path<? extends BankAccount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBankAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBankAccount(PathMetadata metadata, PathInits inits) {
        this(BankAccount.class, metadata, inits);
    }

    public QBankAccount(Class<? extends BankAccount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bankCode = inits.isInitialized("bankCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("bankCode"), inits.get("bankCode")) : null;
    }

}

