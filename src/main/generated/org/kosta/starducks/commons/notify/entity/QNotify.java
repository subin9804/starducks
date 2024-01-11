package org.kosta.starducks.commons.notify.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotify is a Querydsl query type for Notify
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotify extends EntityPathBase<Notify> {

    private static final long serialVersionUID = -402463913L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotify notify = new QNotify("notify");

    public final org.kosta.starducks.commons.QBaseTimeEntity _super = new org.kosta.starducks.commons.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<Notify.NotificationType> notificationType = createEnum("notificationType", Notify.NotificationType.class);

    public final org.kosta.starducks.hr.entity.QEmployee receiver;

    public final StringPath url = createString("url");

    public QNotify(String variable) {
        this(Notify.class, forVariable(variable), INITS);
    }

    public QNotify(Path<? extends Notify> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotify(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotify(PathMetadata metadata, PathInits inits) {
        this(Notify.class, metadata, inits);
    }

    public QNotify(Class<? extends Notify> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiver = inits.isInitialized("receiver") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("receiver"), inits.get("receiver")) : null;
    }

}

