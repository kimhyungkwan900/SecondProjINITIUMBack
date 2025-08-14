package com.secondprojinitiumback.admin.coreCompetency.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCoreCompetencyQuestion is a Querydsl query type for CoreCompetencyQuestion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoreCompetencyQuestion extends EntityPathBase<CoreCompetencyQuestion> {

    private static final long serialVersionUID = -457248883L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCoreCompetencyQuestion coreCompetencyQuestion = new QCoreCompetencyQuestion("coreCompetencyQuestion");

    public final NumberPath<Integer> answerAllowCount = createNumber("answerAllowCount", Integer.class);

    public final QCoreCompetencyAssessment assessment;

    public final DateTimePath<java.util.Date> deletedAt = createDateTime("deletedAt", java.util.Date.class);

    public final StringPath deletedYn = createString("deletedYn");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> optionCount = createNumber("optionCount", Integer.class);

    public final NumberPath<Integer> questionNo = createNumber("questionNo", Integer.class);

    public final ListPath<ResponseChoiceOption, QResponseChoiceOption> responseChoiceOptions = this.<ResponseChoiceOption, QResponseChoiceOption>createList("responseChoiceOptions", ResponseChoiceOption.class, QResponseChoiceOption.class, PathInits.DIRECT2);

    public final QSubCompetencyCategory subCompetencyCategory;

    public QCoreCompetencyQuestion(String variable) {
        this(CoreCompetencyQuestion.class, forVariable(variable), INITS);
    }

    public QCoreCompetencyQuestion(Path<? extends CoreCompetencyQuestion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCoreCompetencyQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCoreCompetencyQuestion(PathMetadata metadata, PathInits inits) {
        this(CoreCompetencyQuestion.class, metadata, inits);
    }

    public QCoreCompetencyQuestion(Class<? extends CoreCompetencyQuestion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.assessment = inits.isInitialized("assessment") ? new QCoreCompetencyAssessment(forProperty("assessment"), inits.get("assessment")) : null;
        this.subCompetencyCategory = inits.isInitialized("subCompetencyCategory") ? new QSubCompetencyCategory(forProperty("subCompetencyCategory"), inits.get("subCompetencyCategory")) : null;
    }

}

