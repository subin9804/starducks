package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreInboundProduct is a Querydsl query type for StoreInboundProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreInboundProduct extends EntityPathBase<StoreInboundProduct> {

    private static final long serialVersionUID = -1429567816L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreInboundProduct storeInboundProduct = new QStoreInboundProduct("storeInboundProduct");

    public final NumberPath<Long> inboundPrice = createNumber("inboundPrice", Long.class);

    public final NumberPath<Integer> inboundQuantity = createNumber("inboundQuantity", Integer.class);

    public final org.kosta.starducks.generalAffairs.entity.QProduct product;

    public final QStoreInbound storeInbound;

    public final NumberPath<Long> storeInboundProductId = createNumber("storeInboundProductId", Long.class);

    public QStoreInboundProduct(String variable) {
        this(StoreInboundProduct.class, forVariable(variable), INITS);
    }

    public QStoreInboundProduct(Path<? extends StoreInboundProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreInboundProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreInboundProduct(PathMetadata metadata, PathInits inits) {
        this(StoreInboundProduct.class, metadata, inits);
    }

    public QStoreInboundProduct(Class<? extends StoreInboundProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new org.kosta.starducks.generalAffairs.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.storeInbound = inits.isInitialized("storeInbound") ? new QStoreInbound(forProperty("storeInbound"), inits.get("storeInbound")) : null;
    }

}

