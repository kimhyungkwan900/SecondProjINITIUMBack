package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QResponseChoiceOption is a Querydsl query type for ResponseChoiceOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResponseChoiceOption extends EntityPathBase<ResponseChoiceOption> {

    private static final long serialVersionUID = -1261201982L;

    public static final QResponseChoiceOption responseChoiceOption = new QResponseChoiceOption("responseChoiceOption");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public QResponseChoiceOption(String variable) {
        super(ResponseChoiceOption.class, forVariable(variable));
    }

    public QResponseChoiceOption(Path<? extends ResponseChoiceOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResponseChoiceOption(PathMetadata metadata) {
        super(ResponseChoiceOption.class, metadata);
    }

}

