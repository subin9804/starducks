package org.kosta.starducks.fina.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClientAndProduct is a Querydsl query type for ClientAndProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClientAndProduct extends EntityPathBase<ClientAndProduct> {

    private static final long serialVersionUID = -376694024L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClientAndProduct clientAndProduct = new QClientAndProduct("clientAndProduct");

    public final NumberPath<Long> cliendNo = createNumber("cliendNo", Long.class);

    public final org.kosta.starducks.generalAffairs.entity.QVendor vendor;

    public QClientAndProduct(String variable) {
        this(ClientAndProduct.class, forVariable(variable), INITS);
    }

    public QClientAndProduct(Path<? extends ClientAndProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClientAndProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClientAndProduct(PathMetadata metadata, PathInits inits) {
        this(ClientAndProduct.class, metadata, inits);
    }

    public QClientAndProduct(Class<? extends ClientAndProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new org.kosta.starducks.generalAffairs.entity.QVendor(forProperty("vendor")) : null;
    }

}

