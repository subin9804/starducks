package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWarehouseInboundProduct is a Querydsl query type for WarehouseInboundProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWarehouseInboundProduct extends EntityPathBase<WarehouseInboundProduct> {

    private static final long serialVersionUID = 1443941530L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWarehouseInboundProduct warehouseInboundProduct = new QWarehouseInboundProduct("warehouseInboundProduct");

    public final NumberPath<Long> inboundPrice = createNumber("inboundPrice", Long.class);

    public final NumberPath<Integer> inboundQuantity = createNumber("inboundQuantity", Integer.class);

    public final org.kosta.starducks.generalAffairs.entity.QProduct product;

    public final QWarehouseInbound warehouseInbound;

    public final NumberPath<Long> warehouseInboundProductId = createNumber("warehouseInboundProductId", Long.class);

    public QWarehouseInboundProduct(String variable) {
        this(WarehouseInboundProduct.class, forVariable(variable), INITS);
    }

    public QWarehouseInboundProduct(Path<? extends WarehouseInboundProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWarehouseInboundProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWarehouseInboundProduct(PathMetadata metadata, PathInits inits) {
        this(WarehouseInboundProduct.class, metadata, inits);
    }

    public QWarehouseInboundProduct(Class<? extends WarehouseInboundProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new org.kosta.starducks.generalAffairs.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.warehouseInbound = inits.isInitialized("warehouseInbound") ? new QWarehouseInbound(forProperty("warehouseInbound"), inits.get("warehouseInbound")) : null;
    }

}

