package org.kosta.starducks.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QApprover is a Querydsl query type for Approver
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApprover extends EntityPathBase<Approval> {

    private static final long serialVersionUID = 336514527L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QApprover approver = new QApprover("approver");

    public final NumberPath<Long> approvalId = createNumber("approvalId", Long.class);

    public final QDocument doc;

    public final NumberPath<Long> docId = createNumber("docId", Long.class);

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public QApprover(String variable) {
        this(Approval.class, forVariable(variable), INITS);
    }

    public QApprover(Path<? extends Approval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QApprover(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QApprover(PathMetadata metadata, PathInits inits) {
        this(Approval.class, metadata, inits);
    }

    public QApprover(Class<? extends Approval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.doc = inits.isInitialized("doc") ? new QDocument(forProperty("doc"), inits.get("doc")) : null;
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee")) : null;
    }

}

