package com.secondprojinitiumback.admin.extracurricular.domain.test;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStdntInfo is a Querydsl query type for StdntInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStdntInfo extends EntityPathBase<StdntInfo> {

    private static final long serialVersionUID = -1088101752L;

    public static final QStdntInfo stdntInfo = new QStdntInfo("stdntInfo");

    public final StringPath stdntNo = createString("stdntNo");

    public QStdntInfo(String variable) {
        super(StdntInfo.class, forVariable(variable));
    }

    public QStdntInfo(Path<? extends StdntInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStdntInfo(PathMetadata metadata) {
        super(StdntInfo.class, metadata);
    }

}

