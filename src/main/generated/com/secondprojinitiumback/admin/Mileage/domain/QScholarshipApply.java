package com.secondprojinitiumback.admin.Mileage.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScholarshipApply is a Querydsl query type for ScholarshipApply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScholarshipApply extends EntityPathBase<ScholarshipApply> {

    private static final long serialVersionUID = -1758370145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScholarshipApply scholarshipApply = new QScholarshipApply("scholarshipApply");

    public final NumberPath<Integer> accumulatedMileage = createNumber("accumulatedMileage", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> applyDate = createDateTime("applyDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> approveDate = createDateTime("approveDate", java.time.LocalDateTime.class);

    public final com.secondprojinitiumback.common.bank.domain.QBankAccount bankAccount;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> paymentAmount = createNumber("paymentAmount", java.math.BigDecimal.class);

    public final DateTimePath<java.time.LocalDateTime> rejectDate = createDateTime("rejectDate", java.time.LocalDateTime.class);

    public final StringPath rejectReason = createString("rejectReason");

    public final com.secondprojinitiumback.common.domain.QCommonCode stateCode;

    public final com.secondprojinitiumback.user.student.domain.QStudent student;

    public QScholarshipApply(String variable) {
        this(ScholarshipApply.class, forVariable(variable), INITS);
    }

    public QScholarshipApply(Path<? extends ScholarshipApply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScholarshipApply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScholarshipApply(PathMetadata metadata, PathInits inits) {
        this(ScholarshipApply.class, metadata, inits);
    }

    public QScholarshipApply(Class<? extends ScholarshipApply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bankAccount = inits.isInitialized("bankAccount") ? new com.secondprojinitiumback.common.bank.domain.QBankAccount(forProperty("bankAccount"), inits.get("bankAccount")) : null;
        this.stateCode = inits.isInitialized("stateCode") ? new com.secondprojinitiumback.common.domain.QCommonCode(forProperty("stateCode"), inits.get("stateCode")) : null;
        this.student = inits.isInitialized("student") ? new com.secondprojinitiumback.user.student.domain.QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

