package com.secondprojinitiumback.admin.extracurricular.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExtracurricularProgram is a Querydsl query type for ExtracurricularProgram
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExtracurricularProgram extends EntityPathBase<ExtracurricularProgram> {

    private static final long serialVersionUID = 1694585005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExtracurricularProgram extracurricularProgram = new QExtracurricularProgram("extracurricularProgram");

    public final StringPath cndCn = createString("cndCn");

    public final DateTimePath<java.time.LocalDateTime> eduAplyBgngDt = createDateTime("eduAplyBgngDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> eduAplyDt = createDateTime("eduAplyDt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> eduAplyEndDt = createDateTime("eduAplyEndDt", java.time.LocalDateTime.class);

    public final DatePath<java.time.LocalDate> eduBgngYmd = createDate("eduBgngYmd", java.time.LocalDate.class);

    public final StringPath eduDtlCn = createString("eduDtlCn");

    public final DatePath<java.time.LocalDate> eduEndYmd = createDate("eduEndYmd", java.time.LocalDate.class);

    public final EnumPath<com.secondprojinitiumback.admin.extracurricular.domain.enums.EduGndrLmt> eduGndrLmt = createEnum("eduGndrLmt", com.secondprojinitiumback.admin.extracurricular.domain.enums.EduGndrLmt.class);

    public final NumberPath<Integer> eduMlg = createNumber("eduMlg", Integer.class);

    public final NumberPath<Long> eduMngId = createNumber("eduMngId", Long.class);

    public final StringPath eduNm = createString("eduNm");

    public final StringPath eduPlcNm = createString("eduPlcNm");

    public final StringPath eduPrps = createString("eduPrps");

    public final NumberPath<Integer> eduPtcpNope = createNumber("eduPtcpNope", Integer.class);

    public final EnumPath<com.secondprojinitiumback.admin.extracurricular.domain.enums.EduSlctnType> eduSlctnType = createEnum("eduSlctnType", com.secondprojinitiumback.admin.extracurricular.domain.enums.EduSlctnType.class);

    public final EnumPath<com.secondprojinitiumback.admin.extracurricular.domain.enums.EduTrgtLmt> eduTrgtLmt = createEnum("eduTrgtLmt", com.secondprojinitiumback.admin.extracurricular.domain.enums.EduTrgtLmt.class);

    public final EnumPath<com.secondprojinitiumback.admin.extracurricular.domain.enums.EduType> eduType = createEnum("eduType", com.secondprojinitiumback.admin.extracurricular.domain.enums.EduType.class);

    public final com.secondprojinitiumback.admin.extracurricular.domain.test.QEmpInfo empInfo;

    public final QExtracurricularCategory extracurricularCategory;

    public final StringPath field = createString("field");

    public final StringPath fileNo = createString("fileNo");

    public final DateTimePath<java.time.LocalDateTime> sttsChgDt = createDateTime("sttsChgDt", java.time.LocalDateTime.class);

    public final EnumPath<com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm> sttsNm = createEnum("sttsNm", com.secondprojinitiumback.admin.extracurricular.domain.enums.SttsNm.class);

    public QExtracurricularProgram(String variable) {
        this(ExtracurricularProgram.class, forVariable(variable), INITS);
    }

    public QExtracurricularProgram(Path<? extends ExtracurricularProgram> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExtracurricularProgram(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExtracurricularProgram(PathMetadata metadata, PathInits inits) {
        this(ExtracurricularProgram.class, metadata, inits);
    }

    public QExtracurricularProgram(Class<? extends ExtracurricularProgram> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.empInfo = inits.isInitialized("empInfo") ? new com.secondprojinitiumback.admin.extracurricular.domain.test.QEmpInfo(forProperty("empInfo")) : null;
        this.extracurricularCategory = inits.isInitialized("extracurricularCategory") ? new QExtracurricularCategory(forProperty("extracurricularCategory")) : null;
    }

}

