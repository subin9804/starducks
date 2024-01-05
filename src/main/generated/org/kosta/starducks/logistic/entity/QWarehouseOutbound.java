package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWarehouseOutbound is a Querydsl query type for WarehouseOutbound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWarehouseOutbound extends EntityPathBase<WarehouseOutbound> {

    private static final long serialVersionUID = 1200288596L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWarehouseOutbound warehouseOutbound = new QWarehouseOutbound("warehouseOutbound");

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final ListPath<WarehouseOutboundProduct, QWarehouseOutboundProduct> outboundProducts = this.<WarehouseOutboundProduct, QWarehouseOutboundProduct>createList("outboundProducts", WarehouseOutboundProduct.class, QWarehouseOutboundProduct.class, PathInits.DIRECT2);

    public final NumberPath<Long> totalPrice = createNumber("totalPrice", Long.class);

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> warehouseOutboundDate = createDateTime("warehouseOutboundDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> warehouseoutboundId = createNumber("warehouseoutboundId", Long.class);

    public QWarehouseOutbound(String variable) {
        this(WarehouseOutbound.class, forVariable(variable), INITS);
    }

    public QWarehouseOutbound(Path<? extends WarehouseOutbound> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWarehouseOutbound(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWarehouseOutbound(PathMetadata metadata, PathInits inits) {
        this(WarehouseOutbound.class, metadata, inits);
    }

    public QWarehouseOutbound(Class<? extends WarehouseOutbound> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

