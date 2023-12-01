package org.kosta.starducks.generalAffairs.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVendor is a Querydsl query type for Vendor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVendor extends EntityPathBase<Vendor> {

    private static final long serialVersionUID = 898519753L;

    public static final QVendor vendor = new QVendor("vendor");

    public final StringPath vendorAddress = createString("vendorAddress");

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

