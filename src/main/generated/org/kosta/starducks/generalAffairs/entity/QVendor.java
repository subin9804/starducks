package org.kosta.starducks.generalAffairs.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = 898519753L;

    public static final QVendor vendor = new QVendor("vendor");

    public final EnumPath<org.kosta.starducks.fina.entity.ContractStatus> contractStatus = createEnum("contractStatus", org.kosta.starducks.fina.entity.ContractStatus.class);

    public final ListPath<Product, QProduct> products = this.<Product, QProduct>createList("products", Product.class, QProduct.class, PathInits.DIRECT2);

    public final StringPath vendorAddNo = createString("vendorAddNo");

    public final StringPath vendorAddress = createString("vendorAddress");

    public final EnumPath<org.kosta.starducks.fina.entity.VendorBusinessSector> vendorBusinessSector = createEnum("vendorBusinessSector", org.kosta.starducks.fina.entity.VendorBusinessSector.class);

    public final StringPath vendorDetailAdd = createString("vendorDetailAdd");

    public final NumberPath<Integer> vendorId = createNumber("vendorId", Integer.class);

    public final StringPath vendorName = createString("vendorName");

    public final StringPath vendorRegistNum = createString("vendorRegistNum");

    public final StringPath vendorRepreName = createString("vendorRepreName");

    public final DatePath<java.time.LocalDate> vendorStartDate = createDate("vendorStartDate", java.time.LocalDate.class);

    public final StringPath vendorTelephone = createString("vendorTelephone");

    public QVendor(String variable) {
        super(Vendor.class, forVariable(variable));
    }

    public QVendor(Path<? extends Vendor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVendor(PathMetadata metadata) {
        super(Vendor.class, metadata);
    }

}

