package org.kosta.starducks.generalAffairs.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOffiSchedule is a Querydsl query type for OffiSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOffiSchedule extends EntityPathBase<OffiSchedule> {

    private static final long serialVersionUID = 2113488466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOffiSchedule offiSchedule = new QOffiSchedule("offiSchedule");

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final StringPath notes = createString("notes");

    public final DateTimePath<java.time.LocalDateTime> scheEndDate = createDateTime("scheEndDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> scheNo = createNumber("scheNo", Long.class);

    public final DateTimePath<java.time.LocalDateTime> scheStartDate = createDateTime("scheStartDate", java.time.LocalDateTime.class);

    public final StringPath scheTitle = createString("scheTitle");

    public QOffiSchedule(String variable) {
        this(OffiSchedule.class, forVariable(variable), INITS);
    }

    public QOffiSchedule(Path<? extends OffiSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOffiSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOffiSchedule(PathMetadata metadata, PathInits inits) {
        this(OffiSchedule.class, metadata, inits);
    }

    public QOffiSchedule(Class<? extends OffiSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

