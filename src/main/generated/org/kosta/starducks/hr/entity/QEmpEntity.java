package org.kosta.starducks.hr.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmpEntity is a Querydsl query type for EmpEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmpEntity extends EntityPathBase<EmpEntity> {

    private static final long serialVersionUID = 983584098L;

    public static final QEmpEntity empEntity = new QEmpEntity("empEntity");

    public final StringPath addr = createString("addr");

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final StringPath dAddr = createString("dAddr");

    public final StringPath email = createString("email");

    public final NumberPath<Long> empId = createNumber("empId", Long.class);

    public final StringPath empName = createString("empName");

    public final StringPath empTel = createString("empTel");

    public final StringPath gender = createString("gender");

    public final DatePath<java.time.LocalDate> joinDate = createDate("joinDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> leaceDate = createDate("leaceDate", java.time.LocalDate.class);

    public final EnumPath<org.kosta.starducks.roles.Position> position = createEnum("position", org.kosta.starducks.roles.Position.class);

    public final StringPath pwd = createString("pwd");

    public final BooleanPath status = createBoolean("status");

    public QEmpEntity(String variable) {
        super(EmpEntity.class, forVariable(variable));
    }

    public QEmpEntity(Path<? extends EmpEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmpEntity(PathMetadata metadata) {
        super(EmpEntity.class, metadata);
    }

}

