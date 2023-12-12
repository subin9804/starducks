package org.kosta.starducks.fina.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 1303586380L;

    public static final QStore store = new QStore("store");

    public final StringPath addNo = createString("addNo");

    public final NumberPath<Long> businessNum = createNumber("businessNum", Long.class);

    public final StringPath storeAddr = createString("storeAddr");

    public final StringPath storeDetailAddr = createString("storeDetailAddr");

    public final EnumPath<StoreManager> storeManager = createEnum("storeManager", StoreManager.class);

    public final StringPath storeName = createString("storeName");

    public final NumberPath<Long> storeNo = createNumber("storeNo", Long.class);

    public final DatePath<java.time.LocalDate> storeOpenDate = createDate("storeOpenDate", java.time.LocalDate.class);

    public final EnumPath<StoreOperationalYn> storeOperationalYn = createEnum("storeOperationalYn", StoreOperationalYn.class);

    public QStore(String variable) {
        super(Store.class, forVariable(variable));
    }

    public QStore(Path<? extends Store> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStore(PathMetadata metadata) {
        super(Store.class, metadata);
    }

}

