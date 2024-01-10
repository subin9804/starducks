package org.kosta.starducks.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocument is a Querydsl query type for Document
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocument extends EntityPathBase<Document> {

    private static final long serialVersionUID = 12990517L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocument document = new QDocument("document");

    public final ListPath<Approval, QApproval> approvals = this.<Approval, QApproval>createList("approvals", Approval.class, QApproval.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> apvDeadline = createDateTime("apvDeadline", java.time.LocalDateTime.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath docContent = createString("docContent");

    public final DateTimePath<java.time.LocalDateTime> docDate = createDateTime("docDate", java.time.LocalDateTime.class);

    public final QDocForm docForm;

    public final NumberPath<Long> docId = createNumber("docId", Long.class);

    public final EnumPath<DocStatus> docStatus = createEnum("docStatus", DocStatus.class);

    public final StringPath docTitle = createString("docTitle");

    public final DateTimePath<java.time.LocalDateTime> docUpdateDate = createDateTime("docUpdateDate", java.time.LocalDateTime.class);

    public final org.kosta.starducks.hr.entity.QEmployee docWriter;

    public final DatePath<java.time.LocalDate> orderDeadline = createDate("orderDeadline", java.time.LocalDate.class);

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final StringPath refEmpIds = createString("refEmpIds");

    public final BooleanPath urgent = createBoolean("urgent");

    public final org.kosta.starducks.generalAffairs.entity.QVendor vendor;

    public QDocument(String variable) {
        this(Document.class, forVariable(variable), INITS);
    }

    public QDocument(Path<? extends Document> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocument(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocument(PathMetadata metadata, PathInits inits) {
        this(Document.class, metadata, inits);
    }

    public QDocument(Class<? extends Document> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.docForm = inits.isInitialized("docForm") ? new QDocForm(forProperty("docForm")) : null;
        this.docWriter = inits.isInitialized("docWriter") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("docWriter"), inits.get("docWriter")) : null;
        this.vendor = inits.isInitialized("vendor") ? new org.kosta.starducks.generalAffairs.entity.QVendor(forProperty("vendor")) : null;
    }

}

