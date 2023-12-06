package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBranchInventory is a Querydsl query type for BranchInventory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBranchInventory extends EntityPathBase<BranchInventory> {

    private static final long serialVersionUID = -1078373733L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBranchInventory branchInventory = new QBranchInventory("branchInventory");

    public final QBranch branch;

    public final NumberPath<Long> inventoryCode = createNumber("inventoryCode", Long.class);

    public final NumberPath<Integer> inventoryQuantity = createNumber("inventoryQuantity", Integer.class);

    public final DateTimePath<java.util.Date> LastUpdateDate = createDateTime("LastUpdateDate", java.util.Date.class);

    public final org.kosta.starducks.generalAffairs.entity.QProduct product;

    public QBranchInventory(String variable) {
        this(BranchInventory.class, forVariable(variable), INITS);
    }

    public QBranchInventory(Path<? extends BranchInventory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBranchInventory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBranchInventory(PathMetadata metadata, PathInits inits) {
        this(BranchInventory.class, metadata, inits);
    }

    public QBranchInventory(Class<? extends BranchInventory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branch = inits.isInitialized("branch") ? new QBranch(forProperty("branch")) : null;
        this.product = inits.isInitialized("product") ? new org.kosta.starducks.generalAffairs.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

