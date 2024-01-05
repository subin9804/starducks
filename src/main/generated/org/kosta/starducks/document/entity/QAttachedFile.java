package org.kosta.starducks.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachedFile is a Querydsl query type for AttachedFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttachedFile extends EntityPathBase<AttachedFile> {

    private static final long serialVersionUID = 982171482L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachedFile attachedFile = new QAttachedFile("attachedFile");

    public final QDocument document;

    public final StringPath fileExtension = createString("fileExtension");

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public QAttachedFile(String variable) {
        this(AttachedFile.class, forVariable(variable), INITS);
    }

    public QAttachedFile(Path<? extends AttachedFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachedFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachedFile(PathMetadata metadata, PathInits inits) {
        this(AttachedFile.class, metadata, inits);
    }

    public QAttachedFile(Class<? extends AttachedFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.document = inits.isInitialized("document") ? new QDocument(forProperty("document"), inits.get("document")) : null;
    }

}

