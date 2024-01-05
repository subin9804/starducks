package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWarehouseOutboundProduct is a Querydsl query type for WarehouseOutboundProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWarehouseOutboundProduct extends EntityPathBase<WarehouseOutboundProduct> {

    private static final long serialVersionUID = 762759931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWarehouseOutboundProduct warehouseOutboundProduct = new QWarehouseOutboundProduct("warehouseOutboundProduct");

    public final NumberPath<Long> outboundPrice = createNumber("outboundPrice", Long.class);

    public final NumberPath<Integer> outboundQuantity = createNumber("outboundQuantity", Integer.class);

    public final org.kosta.starducks.generalAffairs.entity.QProduct product;

    public final QWarehouseOutbound warehouseOutbound;

    public final NumberPath<Long> warehouseOutboundProductId = createNumber("warehouseOutboundProductId", Long.class);

    public QWarehouseOutboundProduct(String variable) {
        this(WarehouseOutboundProduct.class, forVariable(variable), INITS);
    }

    public QWarehouseOutboundProduct(Path<? extends WarehouseOutboundProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWarehouseOutboundProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWarehouseOutboundProduct(PathMetadata metadata, PathInits inits) {
        this(WarehouseOutboundProduct.class, metadata, inits);
    }

    public QWarehouseOutboundProduct(Class<? extends WarehouseOutboundProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new org.kosta.starducks.generalAffairs.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.warehouseOutbound = inits.isInitialized("warehouseOutbound") ? new QWarehouseOutbound(forProperty("warehouseOutbound"), inits.get("warehouseOutbound")) : null;
    }

}

