package org.kosta.starducks.generalAffairs.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 1427361678L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final org.kosta.starducks.commons.QBaseTimeEntity _super = new org.kosta.starducks.commons.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final ListPath<org.kosta.starducks.logistic.entity.StoreInventory, org.kosta.starducks.logistic.entity.QStoreInventory> inventories = this.<org.kosta.starducks.logistic.entity.StoreInventory, org.kosta.starducks.logistic.entity.QStoreInventory>createList("inventories", org.kosta.starducks.logistic.entity.StoreInventory.class, org.kosta.starducks.logistic.entity.QStoreInventory.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<ProductCategory> productCategory = createEnum("productCategory", ProductCategory.class);

    public final NumberPath<Integer> productCnt = createNumber("productCnt", Integer.class);

    public final NumberPath<Long> productCode = createNumber("productCode", Long.class);

    public final StringPath productName = createString("productName");

    public final NumberPath<Long> productPrice = createNumber("productPrice", Long.class);

    public final BooleanPath productSelling = createBoolean("productSelling");

    public final EnumPath<ProductUnit> productUnit = createEnum("productUnit", ProductUnit.class);

    public final QVendor vendor;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vendor = inits.isInitialized("vendor") ? new QVendor(forProperty("vendor")) : null;
    }

}

