package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWarehouseInbound is a Querydsl query type for WarehouseInbound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWarehouseInbound extends EntityPathBase<WarehouseInbound> {

    private static final long serialVersionUID = -930875755L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWarehouseInbound warehouseInbound = new QWarehouseInbound("warehouseInbound");

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final ListPath<WarehouseInboundProduct, QWarehouseInboundProduct> inboundProducts = this.<WarehouseInboundProduct, QWarehouseInboundProduct>createList("inboundProducts", WarehouseInboundProduct.class, QWarehouseInboundProduct.class, PathInits.DIRECT2);

    public final NumberPath<Long> totalPrice = createNumber("totalPrice", Long.class);

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> warehouseInboundDate = createDateTime("warehouseInboundDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> warehouseInboundId = createNumber("warehouseInboundId", Long.class);

    public QWarehouseInbound(String variable) {
        this(WarehouseInbound.class, forVariable(variable), INITS);
    }

    public QWarehouseInbound(Path<? extends WarehouseInbound> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWarehouseInbound(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWarehouseInbound(PathMetadata metadata, PathInits inits) {
        this(WarehouseInbound.class, metadata, inits);
    }

    public QWarehouseInbound(Class<? extends WarehouseInbound> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

