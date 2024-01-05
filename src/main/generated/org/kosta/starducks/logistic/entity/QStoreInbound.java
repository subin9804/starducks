package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreInbound is a Querydsl query type for StoreInbound
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreInbound extends EntityPathBase<StoreInbound> {

    private static final long serialVersionUID = 1965965879L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreInbound storeInbound = new QStoreInbound("storeInbound");

    public final ListPath<StoreInboundProduct, QStoreInboundProduct> inboundProducts = this.<StoreInboundProduct, QStoreInboundProduct>createList("inboundProducts", StoreInboundProduct.class, QStoreInboundProduct.class, PathInits.DIRECT2);

    public final org.kosta.starducks.fina.entity.QStore store;

    public final DateTimePath<java.time.LocalDateTime> storeInboundDate = createDateTime("storeInboundDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> StoreInboundId = createNumber("StoreInboundId", Long.class);

    public final NumberPath<Long> totalPrice = createNumber("totalPrice", Long.class);

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public QStoreInbound(String variable) {
        this(StoreInbound.class, forVariable(variable), INITS);
    }

    public QStoreInbound(Path<? extends StoreInbound> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreInbound(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreInbound(PathMetadata metadata, PathInits inits) {
        this(StoreInbound.class, metadata, inits);
    }

    public QStoreInbound(Class<? extends StoreInbound> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new org.kosta.starducks.fina.entity.QStore(forProperty("store"), inits.get("store")) : null;
    }

}

