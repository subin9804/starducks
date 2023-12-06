package org.kosta.starducks.logistic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBranch is a Querydsl query type for Branch
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBranch extends EntityPathBase<Branch> {

    private static final long serialVersionUID = -1454895167L;

    public static final QBranch branch = new QBranch("branch");

    public final StringPath branchAddress = createString("branchAddress");

    public final BooleanPath branchClosureStatus = createBoolean("branchClosureStatus");

    public final NumberPath<Long> branchId = createNumber("branchId", Long.class);

    public final StringPath branchRegistNum = createString("branchRegistNum");

    public final NumberPath<Integer> branchTelephone = createNumber("branchTelephone", Integer.class);

    public QBranch(String variable) {
        super(Branch.class, forVariable(variable));
    }

    public QBranch(Path<? extends Branch> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBranch(PathMetadata metadata) {
        super(Branch.class, metadata);
    }

}

