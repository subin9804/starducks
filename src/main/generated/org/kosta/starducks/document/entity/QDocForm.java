package org.kosta.starducks.document.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDocForm is a Querydsl query type for DocForm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDocForm extends EntityPathBase<DocForm> {

    private static final long serialVersionUID = 1938683842L;

    public static final QDocForm docForm = new QDocForm("docForm");

    public final StringPath formCode = createString("formCode");

    public final StringPath formName = createString("formName");

    public final StringPath formNameEn = createString("formNameEn");

    public QDocForm(String variable) {
        super(DocForm.class, forVariable(variable));
    }

    public QDocForm(Path<? extends DocForm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDocForm(PathMetadata metadata) {
        super(DocForm.class, metadata);
    }

}

