package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreInventory is a Querydsl query type for StoreInventory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStoreInventory extends EntityPathBase<StoreInventory> {

    private static final long serialVersionUID = -214759270L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreInventory storeInventory = new QStoreInventory("storeInventory");

    public final org.kosta.starducks.commons.QBaseTimeEntity _super = new org.kosta.starducks.commons.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QStoreInventoryId id;

    public final NumberPath<Integer> inventoryCnt = createNumber("inventoryCnt", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final org.kosta.starducks.generalAffairs.entity.QProduct product;

    public final org.kosta.starducks.fina.entity.QStore store;

    public QStoreInventory(String variable) {
        this(StoreInventory.class, forVariable(variable), INITS);
    }

    public QStoreInventory(Path<? extends StoreInventory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreInventory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreInventory(PathMetadata metadata, PathInits inits) {
        this(StoreInventory.class, metadata, inits);
    }

    public QStoreInventory(Class<? extends StoreInventory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QStoreInventoryId(forProperty("id")) : null;
        this.product = inits.isInitialized("product") ? new org.kosta.starducks.generalAffairs.entity.QProduct(forProperty("product"), inits.get("product")) : null;
        this.store = inits.isInitialized("store") ? new org.kosta.starducks.fina.entity.QStore(forProperty("store"), inits.get("store")) : null;
    }

}

