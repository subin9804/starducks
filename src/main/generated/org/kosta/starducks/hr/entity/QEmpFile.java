package org.kosta.starducks.hr.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmpFile is a Querydsl query type for EmpFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmpFile extends EntityPathBase<EmpFile> {

    private static final long serialVersionUID = -1536380229L;

    public static final QEmpFile empFile = new QEmpFile("empFile");

    public final NumberPath<Long> empId = createNumber("empId", Long.class);

    public final StringPath fileName = createString("fileName");

    public final StringPath filePath = createString("filePath");

    public final StringPath fileUrl = createString("fileUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath type = createString("type");

    public QEmpFile(String variable) {
        super(EmpFile.class, forVariable(variable));
    }

    public QEmpFile(Path<? extends EmpFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmpFile(PathMetadata metadata) {
        super(EmpFile.class, metadata);
    }

}

