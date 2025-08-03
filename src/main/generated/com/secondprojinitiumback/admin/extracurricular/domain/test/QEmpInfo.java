package com.secondprojinitiumback.admin.extracurricular.domain.test;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmpInfo is a Querydsl query type for EmpInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmpInfo extends EntityPathBase<EmpInfo> {

    private static final long serialVersionUID = -40090393L;

    public static final QEmpInfo empInfo = new QEmpInfo("empInfo");

    public final StringPath empNo = createString("empNo");

    public QEmpInfo(String variable) {
        super(EmpInfo.class, forVariable(variable));
    }

    public QEmpInfo(Path<? extends EmpInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmpInfo(PathMetadata metadata) {
        super(EmpInfo.class, metadata);
    }

}

