package org.kosta.starducks.hr.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmployee is a Querydsl query type for Employee
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmployee extends EntityPathBase<Employee> {

    private static final long serialVersionUID = -347861705L;

    public static final QEmployee employee = new QEmployee("employee");

    public final StringPath addr = createString("addr");

    public final DatePath<java.time.LocalDate> birth = createDate("birth", java.time.LocalDate.class);

    public final StringPath dAddr = createString("dAddr");

    public final StringPath dept = createString("dept");

    public final StringPath email = createString("email");

    public final NumberPath<Long> empId = createNumber("empId", Long.class);

    public final StringPath empName = createString("empName");

    public final StringPath empTel = createString("empTel");

    public final StringPath gender = createString("gender");

    public final DatePath<java.time.LocalDate> joinDate = createDate("joinDate", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> leaveDate = createDate("leaveDate", java.time.LocalDate.class);

    public final EnumPath<org.kosta.starducks.roles.Position> position = createEnum("position", org.kosta.starducks.roles.Position.class);

    public final StringPath postNo = createString("postNo");

    public final StringPath pwd = createString("pwd");

    public final BooleanPath status = createBoolean("status");

    public QEmployee(String variable) {
        super(Employee.class, forVariable(variable));
    }

    public QEmployee(Path<? extends Employee> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmployee(PathMetadata metadata) {
        super(Employee.class, metadata);
    }

}
