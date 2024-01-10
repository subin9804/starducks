package org.kosta.starducks.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRefEmployee is a Querydsl query type for RefEmployee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefEmployee extends EntityPathBase<RefEmployee> {

    private static final long serialVersionUID = 166185735L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRefEmployee refEmployee = new QRefEmployee("refEmployee");

    public final QDocument document;

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final NumberPath<Long> refEmpId = createNumber("refEmpId", Long.class);

    public QRefEmployee(String variable) {
        this(RefEmployee.class, forVariable(variable), INITS);
    }

    public QRefEmployee(Path<? extends RefEmployee> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRefEmployee(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRefEmployee(PathMetadata metadata, PathInits inits) {
        this(RefEmployee.class, metadata, inits);
    }

    public QRefEmployee(Class<? extends RefEmployee> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

