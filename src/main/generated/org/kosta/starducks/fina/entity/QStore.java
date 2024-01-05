package org.kosta.starducks.fina.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 1303586380L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final StringPath addNo = createString("addNo");

    public final NumberPath<Long> businessNum = createNumber("businessNum", Long.class);

    public final org.kosta.starducks.hr.entity.QEmployee employee;

    public final ListPath<org.kosta.starducks.logistic.entity.StoreInventory, org.kosta.starducks.logistic.entity.QStoreInventory> inventories = this.<org.kosta.starducks.logistic.entity.StoreInventory, org.kosta.starducks.logistic.entity.QStoreInventory>createList("inventories", org.kosta.starducks.logistic.entity.StoreInventory.class, org.kosta.starducks.logistic.entity.QStoreInventory.class, PathInits.DIRECT2);

    public final StringPath storeAddr = createString("storeAddr");

    public final StringPath storeDetailAddr = createString("storeDetailAddr");

    public final StringPath storeName = createString("storeName");

    public final NumberPath<Long> storeNo = createNumber("storeNo", Long.class);

    public final DatePath<java.time.LocalDate> storeOpenDate = createDate("storeOpenDate", java.time.LocalDate.class);

    public final EnumPath<StoreOperationalYn> storeOperationalYn = createEnum("storeOperationalYn", StoreOperationalYn.class);

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.employee = inits.isInitialized("employee") ? new org.kosta.starducks.hr.entity.QEmployee(forProperty("employee"), inits.get("employee")) : null;
    }

}

