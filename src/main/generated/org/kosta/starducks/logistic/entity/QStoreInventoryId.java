package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreInventoryId is a Querydsl query type for StoreInventoryId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QStoreInventoryId extends BeanPath<StoreInventoryId> {

    private static final long serialVersionUID = -225225899L;

    public static final QStoreInventoryId storeInventoryId = new QStoreInventoryId("storeInventoryId");

    public final NumberPath<Long> productCode = createNumber("productCode", Long.class);

    public final NumberPath<Long> storeNo = createNumber("storeNo", Long.class);

    public QStoreInventoryId(String variable) {
        super(StoreInventoryId.class, forVariable(variable));
    }

    public QStoreInventoryId(Path<? extends StoreInventoryId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreInventoryId(PathMetadata metadata) {
        super(StoreInventoryId.class, metadata);
    }

}

