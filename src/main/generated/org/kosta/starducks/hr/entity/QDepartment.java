package org.kosta.starducks.hr.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDepartment is a Querydsl query type for Department
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDepartment extends EntityPathBase<Department> {

    private static final long serialVersionUID = 1392503707L;

    public static final QDepartment department = new QDepartment("department");

    public final NumberPath<Integer> deptId = createNumber("deptId", Integer.class);

    public final StringPath deptName = createString("deptName");

    public final StringPath deptNameEng = createString("deptNameEng");

    public final StringPath deptRepTel = createString("deptRepTel");

    public final ListPath<Employee, QEmployee> emps = this.<Employee, QEmployee>createList("emps", Employee.class, QEmployee.class, PathInits.DIRECT2);

    public QDepartment(String variable) {
        super(Department.class, forVariable(variable));
    }

    public QDepartment(Path<? extends Department> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartment(PathMetadata metadata) {
        super(Department.class, metadata);
    }

}

