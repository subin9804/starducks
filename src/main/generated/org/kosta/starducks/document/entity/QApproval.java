package org.kosta.starducks.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApproval is a Querydsl query type for Approval
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApproval extends EntityPathBase<Approval> {

    private static final long serialVersionUID = 336514397L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApproval approval = new QApproval("approval");

    public final StringPath apvComment = createString("apvComment");

    public final DateTimePath<java.time.LocalDateTime> apvDate = createDateTime("apvDate", java.time.LocalDateTime.class);

    public final org.kosta.starducks.hr.entity.QEmployee apvEmp;

    public final NumberPath<Long> apvId = createNumber("apvId", Long.class);

    public final EnumPath<ApvStatus> apvStatus = createEnum("apvStatus", ApvStatus.class);

    public final NumberPath<Integer> apvStep = createNumber("apvStep", Integer.class);

    public final QDocument document;

    public QApproval(String variable) {
        this(Approval.class, forVariable(variable), INITS);
    }

    public QApproval(Path<? extends Approval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApproval(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApproval(PathMetadata metadata, PathInits inits) {
        this(Approval.class, metadata, inits);
    }

    public QApproval(Class<? extends Approval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.apvEmp = inits.isInitialized("apvEmp") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("apvEmp"), inits.get("apvEmp")) : null;
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
    }

}

