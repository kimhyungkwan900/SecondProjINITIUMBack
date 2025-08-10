package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResponseChoiceOption is a Querydsl query type for ResponseChoiceOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResponseChoiceOption extends EntityPathBase<ResponseChoiceOption> {

    private static final long serialVersionUID = -1261201982L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResponseChoiceOption responseChoiceOption = new QResponseChoiceOption("responseChoiceOption");

    public final StringPath answerType = createString("answerType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath label = createString("label");

    public final NumberPath<Integer> optionNo = createNumber("optionNo", Integer.class);

    public final QCoreCompetencyQuestion question;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public QResponseChoiceOption(String variable) {
        this(ResponseChoiceOption.class, forVariable(variable), INITS);
    }

    public QResponseChoiceOption(Path<? extends ResponseChoiceOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResponseChoiceOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResponseChoiceOption(PathMetadata metadata, PathInits inits) {
        this(ResponseChoiceOption.class, metadata, inits);
    }

    public QResponseChoiceOption(Class<? extends ResponseChoiceOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QCoreCompetencyQuestion(forProperty("question"), inits.get("question")) : null;
    }

}

